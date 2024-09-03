package com.theberdakh.kepket.screens.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.databinding.ItemFoodWithControlsBinding
import com.theberdakh.kepket.databinding.ItemFoodWithControlsHeaderBinding
import com.theberdakh.kepket.screens.adapter.callbacks.ListItemCallback
import com.theberdakh.kepket.screens.adapter.holders.FoodViewHolder
import com.theberdakh.kepket.screens.adapter.holders.HeaderViewHolder
import com.theberdakh.kepket.screens.models.Food
import com.theberdakh.kepket.screens.models.Header
import com.theberdakh.kepket.screens.models.ListItem
import com.theberdakh.kepket.screens.models.ListItemCategory


class FoodListWithHeadersAdapter :
    ListAdapter<ListItem, RecyclerView.ViewHolder>(ListItemCallback()) {

    sealed class ActionType {
        data object Add : ActionType()
        data object Increase : ActionType()
        data object Decrease : ActionType()
    }

    private var onFoodItemClickListener: ((Food, ActionType) -> Unit)? = null
    fun setOnItemClickListener(listener: (Food, ActionType) -> Unit) {
        onFoodItemClickListener = listener
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Food -> ListItemCategory.FOOD.categoryId
            is Header -> ListItemCategory.HEADER.categoryId
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListItemCategory.FOOD.categoryId -> {
                val viewBinding = ItemFoodWithControlsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                FoodViewHolder(viewBinding, onFoodItemClickListener)
            }
            ListItemCategory.HEADER.categoryId -> {
                val viewBinding = ItemFoodWithControlsHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                HeaderViewHolder(viewBinding)
            }
            else -> throw IllegalArgumentException("Unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)
        when (holder) {
            is FoodViewHolder -> holder.bind(getItem(position) as Food)
            is HeaderViewHolder -> holder.bind(getItem(position) as Header)
        }
    }

}
