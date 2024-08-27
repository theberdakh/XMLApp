package com.theberdakh.kepket.screens.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.theberdakh.kepket.R
import com.theberdakh.kepket.common.dialog.MaterialDialogDelegate
import com.theberdakh.kepket.common.dialog.MaterialDialogHelper
import com.theberdakh.kepket.common.dialog.MaterialDialogHelperImpl
import com.theberdakh.kepket.common.snackbar.SnackbarHelper
import com.theberdakh.kepket.common.snackbar.SnackbarHelperDelegate
import com.theberdakh.kepket.common.snackbar.SnackbarHelperImpl
import com.theberdakh.kepket.common.viewbinding.viewBinding
import com.theberdakh.kepket.databinding.ContainerMainFragmentBinding
import com.theberdakh.kepket.screens.adapter.FoodAdapter
import com.theberdakh.kepket.screens.adapter.ParentItemAdapter
import com.theberdakh.kepket.screens.adapter.TableAdapter
import com.theberdakh.kepket.screens.adapter.TableNumber
import com.theberdakh.kepket.screens.common.animation.TextViewAnimations.withFadeAndScale
import com.theberdakh.kepket.screens.common.animation.ViewAnimations.withFadeIn
import com.theberdakh.kepket.screens.common.animation.ViewAnimations.withFadeOut
import com.theberdakh.kepket.screens.common.animation.ViewAnimations.withRotate
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.gone
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
    private val tableAdapter by lazy { TableAdapter() }
    private val layoutManager by lazy { GridLayoutManager(requireContext(), 2) }
    private lateinit var dialogHelper: MaterialDialogDelegate
    private lateinit var snackbarHelper: SnackbarHelper

    private val fakeData = listOf(
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
    private val tables = mutableListOf<TableNumber>()

    private var _selectedFoods = MutableSharedFlow<List<Food>>()
    private val selectedFoods = _selectedFoods.asSharedFlow()
    private val selectedFoodsList = mutableListOf<Food>()

    private var _allFoods = MutableSharedFlow<List<ParentItem>>()
    private val allFoods = _allFoods.asSharedFlow()
    private val allFoodsList = mutableListOf<ParentItem>()

    private val pizza = fakeData.filter { food: Food -> food.category == Category.PIZZA }
    private val burger = fakeData.filter { food: Food -> food.category == Category.BURGER }
    private val drinks = fakeData.filter { food -> food.category == Category.DRINKS }

    private val pizzaHeader = Header(id = -1, name = "Pizza")
    private val burgerHeader = Header(id = -2, name = "Burger")
    private val drinkHeader = Header(id = -3, name = "Drinks")

    override fun onAttach(context: Context) {
        super.onAttach(context)

        dialogHelper =  MaterialDialogDelegate(fragmentActivity = activity ?: requireActivity())

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        snackbarHelper = SnackbarHelperImpl(binding.orderButton)

        addHeader(topItemsSize = 0, pizzaHeader)
        allFoodsList.addAll(pizza)
        addHeader(topItemsSize = pizza.size, burgerHeader)
        allFoodsList.addAll(burger)
        addHeader(topItemsSize = burger.size, drinkHeader)
        allFoodsList.addAll(drinks)

        repeat(49){
            tables.add(TableNumber(it+1))
        }
        tableAdapter.submitList(tables)

        implementAllFoodsRecyclerView()
        implementBottomSheet()
        implementSortChips()
        implementSearchView()

    }


    @SuppressLint("NotifyDataSetChanged")
    private fun implementAllFoodsRecyclerView() {
        binding.mainRecyclerView.adapter = adapter
        binding.mainRecyclerView.layoutManager = layoutManager
        adapter.submitList(allFoodsList)

        allFoods.onEach {
            adapter.submitList(it)
            adapter.notifyDataSetChanged()
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        adapter.onFoodItemIncreaseClickListener { food ->
            viewLifecycleOwner.lifecycleScope.launch {
                selectedFoodsList.add(food)
                _selectedFoods.emit(selectedFoodsList)
            }
        }
        adapter.onFoodItemDecreaseClickListener { food ->
            viewLifecycleOwner.lifecycleScope.launch {
                selectedFoodsList.remove(food)
                _selectedFoods.emit(selectedFoodsList)
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun implementBottomSheet() {

        binding.ordersRecyclerView.adapter = orderAdapter
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.sheet).apply {
            this.isHideable = false
            this.isDraggable = true
            this.skipCollapsed = true
            this.isFitToContents = true
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        binding.recyclerTables.adapter = tableAdapter
        binding.recyclerTables.gone()

        adapter.onFoodItemAddClickListener { food ->

            bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    when(newState){
                        BottomSheetBehavior.STATE_HIDDEN -> {
                            selectedFoodsList.clear()
                            for (food in allFoodsList) {
                                if (food is Food) {
                                    food.quantity = 0
                                }
                            }
                            viewLifecycleOwner.lifecycleScope.launch {
                                _allFoods.emit(allFoodsList)
                                _selectedFoods.emit(selectedFoodsList)
                            }
                        }
                        BottomSheetBehavior.STATE_EXPANDED -> {
                            Timber.d("Expanded")
                        }
                        BottomSheetBehavior.STATE_COLLAPSED -> {
                            Timber.d("Collapsed")
                        }
                        BottomSheetBehavior.STATE_DRAGGING -> {
                            Timber.d("Dragging")
                        }
                        BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                            Timber.d("Half expanded")
                        }
                        BottomSheetBehavior.STATE_SETTLING -> {
                            Timber.d("Settling")
                        }
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {

                }

            })

            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_HIDDEN) {
                binding.ordersRecyclerView.gone()
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }

            viewLifecycleOwner.lifecycleScope.launch {
                selectedFoodsList.add(food)
                _selectedFoods.emit(selectedFoodsList)
            }
        }

        binding.iconClear.setOnClickListener {
            clearOrder()
        }

        implementOrdersRecyclerView()

        var shouldShowTables = false
        binding.orderButton.setOnClickListener {
            if (shouldShowTables){
                clearOrder()
                snackbarHelper.showSnackbar("Order sent successfully!")
                shouldShowTables = false

            } else {
                binding.recyclerTables.withFadeIn()
                shouldShowTables = true
            }


        }

        selectedFoods.onEach {
            val orderSummary = calculateOrderSummary(it)
            orderAdapter.submitList(orderSummary)
            orderAdapter.notifyDataSetChanged()
            val price = it.sumOf { food -> Integer.parseInt(food.price.replace(".", "")) }
            binding.price.withFadeAndScale(price.toString())

        }.launchIn(viewLifecycleOwner.lifecycleScope)

    }

    private fun clearOrder() {
        tableAdapter.unselectAll()
        binding.recyclerTables.withFadeOut()
        selectedFoodsList.clear()
        for (food in allFoodsList) {
            if (food is Food) {
                food.quantity = 0
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            _allFoods.emit(allFoodsList)
            _selectedFoods.emit(selectedFoodsList)
        }
    }

    private fun implementOrdersRecyclerView() {
        binding.ordersRecyclerView.gone()
        var toggle = false

        binding.seeButton.setOnClickListener {
            if (toggle) {
                binding.ordersRecyclerView.gone()
                binding.seeButton.setBackgroundResource(R.drawable.round_open_in_full_24)
                toggle = false
            } else {
                binding.ordersRecyclerView.withFadeIn()
                binding.seeButton.setBackgroundResource(R.drawable.round_close_fullscreen_24)
                toggle = true
            }
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
        val position = allFoodsList.indexOfFirst { it.name.contains(name) }
        Timber.d("position of $name: $position")
        layoutManager.scrollToPositionWithOffset(position, 0)

    }

    private fun showTopHeader(header: String) {
        val position = allFoodsList.indexOfFirst { it.parentItem == ParentItem.HEADER && it.name == header }
        layoutManager.scrollToPositionWithOffset(position, 0)
    }

    private fun addHeader(topItemsSize: Int, header: Header) {
        if (topItemsSize.isEven() && topItemsSize != 0) {
            allFoodsList.add(header)
            allFoodsList.add(header.copy(name = ""))
        } else if (topItemsSize.isOdd()) {
            allFoodsList.add(header.copy(name = ""))
            allFoodsList.add(header)
            allFoodsList.add(header.copy(name = ""))
        } else {
            allFoodsList.add(header)
            allFoodsList.add(header.copy(name = ""))
        }
        viewLifecycleOwner.lifecycleScope.launch {
            _allFoods.emit(allFoodsList)
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
