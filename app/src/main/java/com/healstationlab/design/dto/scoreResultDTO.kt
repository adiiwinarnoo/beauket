package com.healstationlab.design.dto

data class scoreResultDTO(
    val responseCode: String?,
    val message: Any?,
    val `data`: Data?
) {
    data class Data(
        val sensitive: Int?,
        val speckle: Int?,
        val wrinkle: Int?,
        val acne: Int?,
        val blackhead: Int?,
        val blackRimOfEye: Int?,
        val pore: Int?
    )
}