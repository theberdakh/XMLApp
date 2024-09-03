package com.theberdakh.kepket.screens.adapter.callbacks

import androidx.recyclerview.widget.DiffUtil
import com.theberdakh.kepket.screens.models.ListItem

class ListItemCallback: DiffUtil.ItemCallback<ListItem>(){
    override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean {
        return  oldItem.id == newItem.id && oldItem.name == newItem.name
    }
}
