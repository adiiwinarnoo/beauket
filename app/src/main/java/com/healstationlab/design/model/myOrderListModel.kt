package com.healstationlab.design.model

data class myOrderListModel (
        val orderNo : String,
        val create : ArrayList<Int>,
        val imgUrl : String?,
        val orderId : Int,
        val name : String,
        val price : Long,
        val amount : Int
)