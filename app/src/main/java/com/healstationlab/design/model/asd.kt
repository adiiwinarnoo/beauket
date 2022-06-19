package com.healstationlab.design.model

data class asd(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val imageUrl: String,
        val linkUrl: Any,
        val product: Any,
        val positionType: String,
        val linkType: String
    )
}