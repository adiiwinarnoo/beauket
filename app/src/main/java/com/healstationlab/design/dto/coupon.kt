package com.healstationlab.design.dto

data class coupon(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val meta: Meta,
        val user: User,
        val createdAt: List<Int>?,
        val updatedAt: List<Int>?
    ) {
        data class Meta(
            val id: Int,
            val name: String,
            val code: String?,
            val type: Any?,
            val discountPrice: Int,
            val minimumPrice: Int,
            val expiredAtFormatted: Any?,
            val expiredDate: String
        )

        data class User(
            val id: Int,
            val loginId: String,
            val thirdPartyId: Any?,
            val name: Any?,
            val nickname: Any?,
            val email: Any?,
            val contact: Any?,
            val level: Int,
            val points: Int,
            val gender: Any?,
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
            val lastAccessAt: List<Int>,
            val deletedAt: Any?,
            val newPassword: Any?,
            val enabled: Boolean,
            val authorities: Any?,
            val accountNonExpired: Boolean,
            val accountNonLocked: Boolean,
            val credentialsNonExpired: Boolean
        )
    }
}