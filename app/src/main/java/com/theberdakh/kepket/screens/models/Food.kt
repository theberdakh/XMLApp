package com.theberdakh.kepket.screens.models

import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil

data class Food(
    override val id: Int,
    override val name: String,
    val category: Category,
    @DrawableRes val image: Int,
    val price: String,
    var quantity: Int = 0
): ParentItem(id, FOOD, name) {

    companion object {
        object ItemCallback: DiffUtil.ItemCallback<Food>(){
            override fun areItemsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Food, newItem: Food): Boolean {
                return oldItem.name == newItem.name && oldItem.category == newItem.category && oldItem.image == newItem.image
            }
        }
    }


}



