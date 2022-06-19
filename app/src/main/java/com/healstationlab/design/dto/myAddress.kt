package com.healstationlab.design.dto

data class myAddress(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val user: User,
        val name: String,
        val contact: String,
        val email: String,
        val addressCode: String,
        val address: String,
        val addressDetail: String,
        val defaultAddress: Boolean
    ) {
        data class User(
            val id: Int,
            val loginId: String,
            val thirdPartyId: Any,
            val name: Any,
            val nickname: Any,
            val email: Any,
            val contact: Any,
            val level: Int,
            val points: Int,
            val gender: Any,
            val username: String,
            val password: String,
            val analysisData: Boolean,
            val married: Boolean,
            val hasChildren: Boolean,
            val cosmeticProcedure: Boolean,
            val sensitiveConstitution: Boolean,
            val agreePrivacyPolicy: Boolean,
            val agreeTermsOfUse: Boolean,
            val agreeSms: Boolean,
            val agreeEmail: Boolean,
            val authType: String,
            val lastAccessAt: Any,
            val deletedAt: Any,
            val newPassword: Any,
            val enabled: Boolean,
            val authorities: Any,
            val accountNonExpired: Boolean,
            val accountNonLocked: Boolean,
            val credentialsNonExpired: Boolean
        )
    }
}