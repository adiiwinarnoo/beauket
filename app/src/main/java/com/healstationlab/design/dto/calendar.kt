package com.healstationlab.design.dto

data class calendar(
    val responseCode: String,
    val message: Any,
    val `data`: Data
) {
    data class Data(
        val dates: List<String>,
        val points: Int
    )
}