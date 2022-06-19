package com.healstationlab.design.model

data class CategoryProductModel(
    val id : Any?,
    val name : String?,
    val iconImageUrl : String? = "",
    var isClick : Boolean = false
)