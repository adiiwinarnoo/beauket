package com.healstationlab.design.model

import com.healstationlab.design.dto.banner

data class Banner(
    val imageUrl : String,
    val linkUrl : String?,
    val id : Int,
    val product: banner.Data.Product?
)