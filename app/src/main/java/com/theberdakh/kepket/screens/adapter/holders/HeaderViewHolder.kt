package com.theberdakh.kepket.screens.adapter.holders

import androidx.recyclerview.widget.RecyclerView
import com.theberdakh.kepket.databinding.ItemFoodWithControlsHeaderBinding
import com.theberdakh.kepket.screens.models.Header


class HeaderViewHolder(private val binding: ItemFoodWithControlsHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Header) {
        binding.header.text = item.name
    }
}
