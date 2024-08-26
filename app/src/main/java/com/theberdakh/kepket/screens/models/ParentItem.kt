package com.theberdakh.kepket.screens.models

import androidx.recyclerview.widget.DiffUtil

open class ParentItem(
    open val id: Int,
    val parentItem: Int,
    open val name: String
) {
    companion object {
        const val HEADER = 0
        const val FOOD = 1


    }
}
