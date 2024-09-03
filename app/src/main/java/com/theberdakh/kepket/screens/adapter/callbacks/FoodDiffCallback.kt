package com.theberdakh.kepket.screens.adapter.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.theberdakh.kepket.screens.models.Food

class FoodDiffCallback: DiffUtil.ItemCallback<Food>() {
    override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
        return oldItem.name == newItem.name && oldItem.foodCategory == newItem.foodCategory && oldItem.image == newItem.image && oldItem.id == newItem.id
    }
}
