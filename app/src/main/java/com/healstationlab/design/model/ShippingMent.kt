package com.healstationlab.design.model

import java.io.Serializable

data class ShippingMent(
    var merchant : String?,
    var shippingMent: Int?,
    var isBool : Boolean = false
) : Serializable