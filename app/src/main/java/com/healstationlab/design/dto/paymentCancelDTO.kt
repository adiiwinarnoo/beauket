package com.healstationlab.design.dto

data class paymentCancelDTO(
    val responseCode: String?,
    val message: Any?,
    val `data`: Data?
) {
    data class Data(
        val resultCode: String?,
        val resultMsg: String?,
        val tid: String?,
        val prtcTid: String?,
        val prtcRemains: String?,
        val prtcPrice: String?,
        val prtcType: String?,
        val prtcCnt: String?,
        val prtcDate: String?,
        val prtcTime: String?,
        val receiptInfo: String?
    )
}