package com.healstationlab.design.dto

data class defaultInqueryDTO(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val user: User,
        val content: String,
        val answered: Boolean,
        val type: String,
        val createdAt: List<Int>,
        val updatedAt: List<Int>,
        val deletedAt: Any
    ) {
        data class User(
            val id: Int,
            val loginId: String,
            val thirdPartyId: Any,
            val name: String,
            val nickname: String,
            val email: Any,
            val contact: String,
            val imageUrl: String,
            val level: Int,
            val points: Int,
            val gender: String,
            val username: String,
            val skinType: String,
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
            val admin: Boolean,
            val skinProblems: List<String>,
            val authType: String,
            val recommendProductCode: String,
            val serviceAgreeAt: Any,
            val privacyAgreeAt: Any,
            val newPassword: Any,
            val genderStr: Any,
            val enabled: Boolean,
            val authorities: Any,
            val accountNonExpired: Boolean,
            val accountNonLocked: Boolean,
            val credentialsNonExpired: Boolean,
            val birthDate: String
        )
    }
}