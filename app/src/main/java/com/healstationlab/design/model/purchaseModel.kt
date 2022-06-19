package com.healstationlab.design.model

data class purchaseModel(
    val optionIdCountMap: List<OptionIdCountMap>,
    val shipmentAddressId: Int,
    val usePoint: Int,
    val deliveryInstruction: String,
    val paymentType: String,
    val couponId: Int
) {
    data class OptionIdCountMap(
        val `124`: Int,
        val `125`: Int
    )
}