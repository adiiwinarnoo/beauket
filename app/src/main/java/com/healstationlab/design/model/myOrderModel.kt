package com.healstationlab.design.model

data class myOrderModel(
        val pgTid : String?,
        val paymentType : String,
        val status : String,
        val merchantName : String,
        val name : String,
        val brand : String,
        val imageUrl : String?,
        val amount : Int,
        val deliveryFee : Long,
        val totalPrice : Int,
        val orderItemsId : Int,
        val productId : Int, // 제품 id
        val productOrderId : Int, // 주문 id
        val productOptionId : Int, // 옵션 id
        val invoiceUrl : String?, // 송장번호
        val update : ArrayList<Int>
)