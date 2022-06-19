package com.healstationlab.design.dto

data class getQuestion(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>
) {
    data class Data(
        val id: Int,
        val user: User,
        val answers: List<Answer>
    ) {
        data class User(
            val id: Int,
            val loginId: String,
            val thirdPartyId: Any,
            val name: Any,
            val nickname: String,
            val email: String,
            val contact: String,
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
            val skinProblems: List<String>,
            val authType: String,
            val recommendProductCode: String,
            val newPassword: Any,
            val enabled: Boolean,
            val authorities: Any,
            val accountNonExpired: Boolean,
            val accountNonLocked: Boolean,
            val credentialsNonExpired: Boolean
        )

        data class Answer(
            val id: Int,
            val question: Question,
            val option: Option,
            val recommendProductCode: String,
            val user: User
        ) {
            data class Question(
                val id: Int,
                val content: String,
                val options: List<Option>
            ) {
                data class Option(
                    val id: Int,
                    val content: String,
                    val criteria: Int
                )
            }

            data class Option(
                val id: Int,
                val content: String,
                val criteria: Int
            )

            data class User(
                val id: Int,
                val loginId: String,
                val thirdPartyId: Any,
                val name: Any,
                val nickname: String,
                val email: String,
                val contact: String,
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
                val skinProblems: List<String>,
                val authType: String,
                val recommendProductCode: String,
                val newPassword: Any,
                val enabled: Boolean,
                val authorities: Any,
                val accountNonExpired: Boolean,
                val accountNonLocked: Boolean,
                val credentialsNonExpired: Boolean
            )
        }
    }
}