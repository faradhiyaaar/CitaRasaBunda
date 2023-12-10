package com.faradhy.citarasabunda.ui.screen.keranjang

import com.faradhy.citarasabunda.model.FoodOrder

data class KeranjangState (
    val foodOrders: List<FoodOrder>,
    val totalPrice: Int
)