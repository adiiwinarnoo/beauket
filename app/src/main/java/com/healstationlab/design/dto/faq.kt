package com.healstationlab.design.dto

data class faq(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>,
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int
) {
    data class Data(
        val id: Int,
        val question: String,
        val answer: String
    )
}