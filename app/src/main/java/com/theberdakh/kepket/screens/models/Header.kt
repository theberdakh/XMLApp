package com.theberdakh.kepket.screens.models

import androidx.recyclerview.widget.DiffUtil

data class Header(
    override val name: String,
    override val id: Int
): ParentItem(id, HEADER, name){

    companion object {
        object ItemCallback: DiffUtil.ItemCallback<Header>(){
            override fun areItemsTheSame(oldItem: Header, newItem: Header): Boolean {
                return true
            }

            override fun areContentsTheSame(oldItem: Header, newItem: Header): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}
