package com.theberdakh.kepket.screens.models

import androidx.annotation.DrawableRes

data class Food(
    override val id: Int,
    override val name: String,
    val foodCategory: FoodCategory,
    @DrawableRes val image: Int,
    val price: String,
    var quantity: Int = 0,
    override val listItemCategory: ListItemCategory = ListItemCategory.FOOD,
) : ListItem



