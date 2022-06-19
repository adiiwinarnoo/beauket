package com.healstationlab.design.model

data class testtest(
    val options: List<Option>,
    val shipmentAddressId: Int,
    val usePoint: Int,
    val deliveryInstruction: String,
    val paymentType: String,
    val couponId: Int,
    val orderNo: String
) {
    data class Option(
        val id: Int,
        val count: Int,
        val deliveryCount: Boolean
    )
}