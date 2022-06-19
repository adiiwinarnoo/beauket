package com.healstationlab.design.dto

data class userInfo(
    val responseCode: String,
    val message: Any,
    val `data`: Data
) {
    data class Data(
        val id: Int,
        val loginId: String,
        val thirdPartyId: Any,
        val name: Any,
        val imageUrl : String,
        val nickname: String,
        val email: String?,
        val contact: Any,
        val level: Int,
        val points: Int,
        val gender: String,
        val username: String,
        val skinType: String?,
        val analysisData: Boolean,
        val married: Boolean,
        val hasChildren: Boolean,
        val cosmeticProcedure: Boolean,
        val sensitiveConstitution: Boolean,
        val agreePrivacyPolicy: Boolean,
        val agreeTermsOfUse: Boolean,
        val agreeSms: Boolean,
        val agreeEmail: Boolean,
        val alarmOn: Boolean,
        val skinProblems: List<String>,
        val authType: String,
        val recommendProductCode: String,
        val newPassword: Any,
        val enabled: Boolean,
        val authorities: Any,
        val accountNonExpired: Boolean,
        val accountNonLocked: Boolean,
        val credentialsNonExpired: Boolean,
        val birthDate : String,
        val alarmEventOn : Boolean,
        val alarmNoticeOn : Boolean,
        val alarmOrderStatusOn : Boolean,
        val alarmProductInquiryOn : Boolean,
        val alarmInfoOn : Boolean
    )
}