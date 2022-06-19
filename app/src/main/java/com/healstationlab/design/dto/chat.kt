package com.healstationlab.design.dto

data class chat (
    val responseCode : String,
    val message : String,
    val data : List<ChatData>
)

data class ChatData(
    val id : Int,
    val user : ChatUser?,
    val title : String,
    val contents : String?,
    val top : Boolean,
    val createdDate : String,
    val imageUrls : ArrayList<Any>
)

data class ChatUser(
    val id : Int,
    val loginId : String,
    val thirdPartyId : String,
    val name : String?,
    val imageUrl : String,
    val nickname : String?,
    val email : String,
    val contact : String,
    val gender : String,
    val username : String,
    val password: String,
    val agreePrivacyPolicy : Boolean,
    val agreeTermsOfUse : Boolean,
    val agreeSms : Boolean,
    val agreeEmail : Boolean,
    val authType : Boolean, // 자료형 뭔지
    val enabled : Boolean,
    val authorities : Boolean,
    val birthDate : String,
    val accountNonExpired : Boolean,
    val recommendProductCode : String,
    val skinProblems : ArrayList<String>,
    val accountNonLocked : Boolean,
    val credentialsNonExpired : Boolean
)