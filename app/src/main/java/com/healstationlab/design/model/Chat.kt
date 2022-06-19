package com.healstationlab.design.model

import java.io.Serializable

data class Chat(
    val id: Int?,
    val userId : Int?,
    val nickname: String?,
    val contents: String?,
    val createdDate: String?,
    val recommendProductCode: String?,
    val skinProblems: ArrayList<String>?,
    val imageUrl : String?,
    val imageUrls : ArrayList<Any>?,
    val birthDate : String?
) : Serializable