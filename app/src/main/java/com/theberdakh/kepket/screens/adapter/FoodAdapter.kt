package com.theberdakh.kepket.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.R
import com.theberdakh.kepket.databinding.ItemRecyclerFoodAddBinding
import com.theberdakh.kepket.databinding.ItemRecyclerFoodBinding
import com.theberdakh.kepket.databinding.ItemRecyclerHeaderBinding
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.gone
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.visible
import com.theberdakh.kepket.screens.models.Category
import com.theberdakh.kepket.screens.models.Food

class FoodAdapter : ListAdapter<Food, FoodAdapter.ViewHolder>(Food.Companion.ItemCallback) {

    inner class ViewHolder(private val binding: ItemRecyclerFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind() {
            val food: Food = getItem(adapterPosition)
            binding.apply {
                foodTitle.text = food.name
                foodImage.setImageResource(food.image)
                foodPrice.text = food.price
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodAdapter.ViewHolder {
        return ViewHolder(
            ItemRecyclerFoodBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_recycler_food, parent, false)
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).category) {
            Category.PIZZA -> Category.PIZZA.type
            Category.BURGER -> Category.BURGER.type
            Category.DRINKS -> Category.DRINKS.type
        }
    }

    override fun onBindViewHolder(
        holder: FoodAdapter.ViewHolder,
        position: Int
    ) = holder.bind()

}
