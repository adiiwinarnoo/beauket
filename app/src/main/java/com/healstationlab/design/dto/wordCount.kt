package com.healstationlab.design.dto

data class wordCount(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>
) {
    data class Data(
        val name: String
    )
}