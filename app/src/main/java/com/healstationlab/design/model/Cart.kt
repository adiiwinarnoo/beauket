package com.healstationlab.design.model

import java.io.Serializable

data class Cart(
    var merchant : String? = null,
    var proId : Int? = null,
    var brand : String? = null,
    var name : String? = null,
    var price : Long? = null,
    var count : Long? = null,
    var rating : Double? = null,
    var imageUrl : String? = null,
    var id : Int? = null,
    var optionName : String? = null,
    var shippingPrice : Int? = null,
    var merchantName : String? = null,
    var isChecked : Boolean? = true
) : Serializable