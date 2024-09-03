package com.theberdakh.kepket.screens.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.databinding.ItemFoodWithControlsBinding
import com.theberdakh.kepket.screens.adapter.FoodListWithHeadersAdapter.ActionType
import com.theberdakh.kepket.screens.common.animation.TextViewAnimations.withFadeAndScale
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.gone
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.visible
import com.theberdakh.kepket.screens.models.Food

class FoodViewHolder(
    private val binding: ItemFoodWithControlsBinding,
    private val onFoodItemClickListener: ((Food, ActionType) -> Unit)?
) : RecyclerView.ViewHolder(binding.root) {

    private fun updateQuantity(food: Food) {
        binding.foodQuantity.withFadeAndScale(food.quantity.toString())
    }


    fun bind(food: Food) {
        binding.foodTitle.text = food.name
        binding.foodImage.setImageResource(food.image)
        binding.foodPrice.text = food.price
        binding.foodQuantity.text = food.quantity.toString()


        binding.foodAddButton.setOnClickListener {

            binding.layoutIncreaseDecrease.visible()
            binding.foodAddButton.gone()
            food.quantity += 1
            updateQuantity(food)
            onFoodItemClickListener?.invoke(food, ActionType.Add)

        }

        binding.foodIncreaseButton.setOnClickListener {
            food.quantity += 1
            updateQuantity(food)
            onFoodItemClickListener?.invoke(food, ActionType.Increase)
        }

        binding.foodDecreaseButton.setOnClickListener {

            if (food.quantity == 1) {
                binding.foodAddButton.visible()
                binding.layoutIncreaseDecrease.gone()
            }

            if (food.quantity >= 1) {
                food.quantity -= 1
                updateQuantity(food)
            }

            onFoodItemClickListener?.invoke(food, ActionType.Decrease)
        }
    }
}

