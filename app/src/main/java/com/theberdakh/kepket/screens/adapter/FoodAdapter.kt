package com.theberdakh.kepket.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.R
import com.theberdakh.kepket.databinding.ItemFoodSummaryBinding
import com.theberdakh.kepket.screens.adapter.callbacks.FoodDiffCallback
import com.theberdakh.kepket.screens.models.Food

class FoodAdapter : ListAdapter<Food, FoodAdapter.ViewHolder>(FoodDiffCallback()) {

    inner class ViewHolder(private val binding: ItemFoodSummaryBinding) :
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
            ItemFoodSummaryBinding.bind(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_food_summary, parent, false)
            )
        )
    }


    override fun onBindViewHolder(
        holder: FoodAdapter.ViewHolder,
        position: Int
    ) = holder.bind()

}
