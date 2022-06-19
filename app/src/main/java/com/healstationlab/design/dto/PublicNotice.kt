package com.healstationlab.design.dto

data class PublicNotice(
    val responseCode: String,
    val message: Any,
    val `data`: List<PublicData>,
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int
) {
    data class PublicData(
        val id: Int,
        val title: String,
        val contents: String
    )
}