package com.healstationlab.design.dto

data class pointsDate(
    val responseCode: String?,
    val message: Any?,
    val `data`: Data?
) {
    data class Data(
        val paymentDate: Int?,
        val purchase: Double?,
        val review: Int?
    )
}