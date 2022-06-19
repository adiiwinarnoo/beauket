package com.healstationlab.design.dto

data class kakao(
    val id: Int?,
    val connected_at: String?,
    val properties: Properties?,
    val kakao_account: KakaoAccount?
) {
    data class Properties(
        val nickname: String?,
        val profile_image: String?,
        val thumbnail_image: String?
    )

    data class KakaoAccount(
        val profile_nickname_needs_agreement: Boolean?,
        val profile_image_needs_agreement: Boolean?,
        val profile: Profile?,
        val has_email: Boolean?,
        val email_needs_agreement: Boolean?,
        val is_email_valid: Boolean?,
        val is_email_verified: Boolean?,
        val email: String?,
        val has_birthday: Boolean?,
        val birthday_needs_agreement: Boolean?,
        val birthday: String?,
        val birthday_type: String?,
        val has_gender: Boolean?,
        val gender_needs_agreement: Boolean?,
        val gender: String?
    ) {
        data class Profile(
            val nickname: String?,
            val thumbnail_image_url: String?,
            val profile_image_url: String?,
            val is_default_image: Boolean?
        )
    }
}