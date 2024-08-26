package com.theberdakh.kepket.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.R
import com.theberdakh.kepket.databinding.ItemRecyclerFoodAddBinding
import com.theberdakh.kepket.databinding.ItemRecyclerHeaderBinding
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.gone
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.visible
import com.theberdakh.kepket.screens.models.Food
import com.theberdakh.kepket.screens.models.Header
import com.theberdakh.kepket.screens.models.ParentItem

class ItemCallback: DiffUtil.ItemCallback<ParentItem>(){
    override fun areItemsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ParentItem, newItem: ParentItem): Boolean {
        return  oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}

class ParentItemAdapter :
    ListAdapter<ParentItem, RecyclerView.ViewHolder>(ItemCallback()) {

    private var onFoodItemAddClickListener: ((Food) -> Unit)? = null
    fun onFoodItemAddClickListener(block: ((Food) -> Unit)) {
        onFoodItemAddClickListener = block
    }

    private var onFoodItemIncreaseClickListener: ((Food) -> Unit)? = null
    fun onFoodItemIncreaseClickListener(block: (Food) -> Unit) {
        onFoodItemIncreaseClickListener = block
    }

    private var onFoodItemDecreaseClickListener: ((Food) -> Unit)? = null
    fun onFoodItemDecreaseClickListener(block: (Food) -> Unit) {
        onFoodItemDecreaseClickListener = block
    }

    inner class FoodViewHolder(private val binding: ItemRecyclerFoodAddBinding) :
        RecyclerView.ViewHolder(binding.root) {

            private fun updateQuantity(food: Food){
                binding.foodQuantity.text = food.quantity.toString()
            }

        fun bind() {
            val food = getItem(adapterPosition) as Food
            binding.foodTitle.text = food.name
            binding.foodImage.setImageResource(food.image)
            binding.foodPrice.text = food.price
            binding.foodQuantity.text = food.quantity.toString()

            binding.foodAddButton.setOnClickListener {
                binding.layoutIncreaseDecrease.visible()
                binding.foodAddButton.gone()
                food.quantity += 1
                updateQuantity(food)
                onFoodItemAddClickListener?.invoke(food)
            }

            binding.foodIncreaseButton.setOnClickListener {
                food.quantity +=1
                updateQuantity(food)
                onFoodItemIncreaseClickListener?.invoke(food)
            }

            binding.foodDecreaseButton.setOnClickListener {
                if (food.quantity == 1){
                    binding.foodAddButton.visible()
                    binding.layoutIncreaseDecrease.gone()
                }

                if (food.quantity >= 1){
                    food.quantity -=1
                    updateQuantity(food)
                }

                onFoodItemDecreaseClickListener?.invoke(food)


            }

        }
    }

    inner class HeaderViewHolder(private val binding: ItemRecyclerHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Header) {
            binding.header.text = item.name
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Food -> ParentItem.FOOD
            is Header -> ParentItem.HEADER
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ParentItem.FOOD -> {
                FoodViewHolder(
                    ItemRecyclerFoodAddBinding.bind(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_recycler_food_add, parent, false)
                    )
                )
            }

            ParentItem.HEADER -> {
                HeaderViewHolder(
                    ItemRecyclerHeaderBinding.bind(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.item_recycler_header, parent, false)
                    )
                )
            }

            else -> HeaderViewHolder(
                ItemRecyclerHeaderBinding.bind(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_recycler_header, parent, false)
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (holder) {
            is FoodViewHolder -> holder.bind()
            is HeaderViewHolder -> holder.bind(getItem(position) as Header)
        }
    }

}
