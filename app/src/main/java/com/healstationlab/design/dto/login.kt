package com.healstationlab.design.dto

data class auth(
    val responseCode : String,
    val message : String,
    val data : MansaeLoginData?
)

data class MansaeLoginData(
    val authType : String,
    val username : String,
    val token : String,
    val nickname : String,
    val name : String,
    val id : Int,
    val email : String
)