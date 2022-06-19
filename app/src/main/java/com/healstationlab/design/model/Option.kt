package com.healstationlab.design.model

import java.io.Serializable

data class Option (
        val name : String,
        val price : Long,
        val originalPrice : Int,
        val stocks : Int,
        val id : Int,
        var cnt : Int = 1,
        var merchant : String?,
        var shipment : Int,
        var brand : String,
        var img : String,
        var optionName : String
) : Serializable