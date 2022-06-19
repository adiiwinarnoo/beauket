package com.healstationlab.design.dto

data class pageCount(
    val responseCode: String,
    val message: Any,
    val `data`: Data
) {
    data class Data(
        val reviewCount: Int,
        val orderCount: Int,
        val couponCount: Int,
        val cartItemCount: Int
    )
}