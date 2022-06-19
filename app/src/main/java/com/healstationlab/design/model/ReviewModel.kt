package com.healstationlab.design.model


data class ReviewModel(
        val content : String,
        val imageUrl : String?,
        val rating : Double,
        val nickname : String?,
        val skinProblems : ArrayList<String>?,
        val imageUrls : ArrayList<String>,
        val createdDate : String?,
        val birthDate : String? = null
)