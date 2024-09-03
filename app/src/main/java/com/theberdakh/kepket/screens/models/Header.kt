package com.theberdakh.kepket.screens.models

data class Header(
    override val name: String,
    override val id: Int,
    override val listItemCategory: ListItemCategory = ListItemCategory.HEADER
): ListItem
