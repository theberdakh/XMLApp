package com.theberdakh.kepket.screens.main

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.theberdakh.kepket.R
import com.theberdakh.kepket.common.viewbinding.viewBinding
import com.theberdakh.kepket.databinding.ContainerMainFragmentBinding
import com.theberdakh.kepket.screens.adapter.FoodAdapter
import com.theberdakh.kepket.screens.adapter.ParentItemAdapter
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.gone
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.visible
import com.theberdakh.kepket.screens.models.Category
import com.theberdakh.kepket.screens.models.Food
import com.theberdakh.kepket.screens.models.Header
import com.theberdakh.kepket.screens.models.ParentItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber


class MainFragment : Fragment(R.layout.container_main_fragment) {
    private val binding by viewBinding<ContainerMainFragmentBinding>()
    private val adapter by lazy { ParentItemAdapter() }
    private val orderAdapter by lazy { FoodAdapter() }
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 2) }
    private var _orderFoods = MutableSharedFlow<List<Food>>()
    private val orderFoods = _orderFoods.asSharedFlow()
    private val foods = mutableListOf<Food>()

    private val fakeData =
        listOf(
            Food(3, "Pizza B", Category.BURGER, R.drawable.pizza_margarita, "40.000"),
            Food(4, "Pizza B2", Category.BURGER, R.drawable.pizza_margarita, "40.000"),
            Food(5, "Pizza B2", Category.BURGER, R.drawable.pizza_margarita, "40.000"),
            Food(6, "Pizza B2", Category.BURGER, R.drawable.pizza_margarita, "40.000"),
            Food(7, "Pizza 7", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(8, "Pizza 8", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(9, "Pizza 9", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(10, "Pizza 10", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(11, "Pizza 11", Category.BURGER, R.drawable.pizza_margarita, "40.000"),
            Food(12, "Pizza 12", Category.BURGER, R.drawable.pizza_margarita, "40.000"),
            Food(13, "Pizza 13", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(14, "Pizza 14", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(15, "Pizza 15", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
            Food(21, "Pizza 21", Category.PIZZA, R.drawable.pizza_margarita, "40.000"),
        )


    private val pizza = fakeData.filter { food: Food -> food.category == Category.PIZZA }
    private val burger = fakeData.filter { food: Food -> food.category == Category.BURGER }
    private val drinks = fakeData.filter { food -> food.category == Category.DRINKS }
    private val pizzaHeader = Header(id = -1, name = "Pizza")
    private val burgerHeader = Header(id = -2, name = "Burger")
    private val drinkHeader = Header(id = -3, name = "Drinks")
    private val mix = mutableListOf<ParentItem>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        addHeader(topItemsSize = 0, pizzaHeader)
        mix.addAll(pizza)
        addHeader(topItemsSize = pizza.size, burgerHeader)
        mix.addAll(burger)
        addHeader(topItemsSize = burger.size, drinkHeader)
        mix.addAll(drinks)

        implementRecyclerView()
        implementBottomSheet()
        implementSortChips()
        implementSearchView()

    }


    private fun implementRecyclerView() {
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = layoutManager
        adapter.submitList(mix)


        adapter.onFoodItemIncreaseClickListener { food ->
            viewLifecycleOwner.lifecycleScope.launch {
                foods.add(food)
                _orderFoods.emit(foods)
            }
        }
        adapter.onFoodItemDecreaseClickListener { food ->
            viewLifecycleOwner.lifecycleScope.launch {
                foods.remove(food)
                _orderFoods.emit(foods)
            }
        }

    }

    private fun implementBottomSheet() {

        binding.ordersRecyclerView.adapter = orderAdapter

        val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet).apply {
            this.isHideable = true
            this.isDraggable = true
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        adapter.onFoodItemAddClickListener { food ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            viewLifecycleOwner.lifecycleScope.launch {
                foods.add(food)
                _orderFoods.emit(foods)
            }
        }


        binding.ordersRecyclerView.gone()
        var toggle = false
        binding.seeButton.setOnClickListener {
            if (toggle) {
                binding.ordersRecyclerView.gone()
                binding.seeIcon.setImageResource(R.drawable.round_menu_open_24)
                toggle = false
            } else {
                binding.ordersRecyclerView.visible()
                binding.seeIcon.setImageResource(R.drawable.round_close_24)
                toggle = true
            }

        }

        orderFoods.onEach {

            val orderSummary = calculateOrderSummary(it)

            orderAdapter.submitList(orderSummary)
            orderAdapter.notifyDataSetChanged()
            val price = it.sumOf { food -> Integer.parseInt(food.price.replace(".", "")) }
            binding.price.text = price.toString()
            if (price == 0) {
                bottomSheetBehavior.isHideable = true
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)




        binding.orderButton.setOnClickListener {
        }
    }

    private fun calculateOrderSummary(foods: List<Food>): List<Food> {

        return foods.groupBy { it.name }
            .map { (name, items) ->
                val foodId = items[0].id
                val foodName = items[0].name
                val foodImage = items[0].image
                val foodCategory = items[0].category
                val quantity = items.size
                val totalPrice = items.sumOf { Integer.parseInt(it.price.replace(".", "")) }

                Food(
                    id = foodId,
                    name = "$quantity x $foodName",
                    category = foodCategory,
                    image = foodImage,
                    price = totalPrice.toString(),
                )
            }

    }

    private fun implementSortChips() {
        binding.chipAll.setOnClickListener {
            showTopHeader("Pizza")
        }

        binding.chipBurgers.setOnClickListener {
            showTopHeader("Burger")
        }

        binding.chipPizza.setOnClickListener {
            showTopHeader("Pizza")
        }
        binding.chipDrinks.setOnClickListener {
            showTopHeader("Drinks")
        }
    }

    private fun implementSearchView() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchView.hasFocus()) {
                        binding.searchView.clearFocus()
                    } else {
                        isEnabled = false
                        requireActivity().onBackPressed()
                    }
                }
            })

        binding.searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchFood(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    searchFood(newText)
                }
                return false
            }
        })
    }

    private fun searchFood(name: String) {
        val position = mix.indexOfFirst { it.name.contains(name) }
        Timber.d("position of $name: $position")
        layoutManager.scrollToPositionWithOffset(position, 0)

    }

    private fun showTopHeader(header: String) {
        val position = mix.indexOfFirst { it.parentItem == ParentItem.HEADER && it.name == header }
        layoutManager.scrollToPositionWithOffset(position, 0)
    }

    private fun addHeader(topItemsSize: Int, header: Header) {
        if (topItemsSize.isEven() && topItemsSize != 0) {
            mix.add(header)
            mix.add(header.copy(name = ""))
        } else if (topItemsSize.isOdd()) {
            mix.add(header.copy(name = ""))
            mix.add(header)
            mix.add(header.copy(name = ""))
        } else {
            mix.add(header)
            mix.add(header.copy(name = ""))
        }
    }

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }

    private fun Int.isEven() = this % 2 == 0
    private fun Int.isOdd() = this % 2 != 0

}
