package com.healstationlab.design.dto

data class naverDTO(
    val resultcode: String?,
    val message: String?,
    val response: Response?
) {
    data class Response(
        val id: String?,
        val nickname: String?,
        val profile_image: String?,
        val email: String?
    )
}