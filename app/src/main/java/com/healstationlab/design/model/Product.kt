package com.healstationlab.design.model

data class Product (
    val id : Int,
    val product_img : String?,
    val product_rating_num : Double,
    val product_company : String,
    val product_title : String,
    val product_price : String
)