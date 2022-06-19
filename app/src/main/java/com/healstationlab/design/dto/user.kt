package com.healstationlab.design.dto

data class user(
        val meta : Meta,
        val response : Response_user
)

data class Response_user(
        val next : String,
        val lists : List<List_user>
)

data class List_user(
        val id : Long,
        val merchant_id : Long,
        val name : String,
        val gender : Int,
        val birthday : String,
        val phone_cc : Int,
        val phone_number : String,
        val marital_status : Int,
        val has_baby : Int,
        val has_beauty : Int,
        val is_irritability : Int,
        val target : Target,
        val store_id : Long,
        val created_at : Long
)

data class Target(
        val pore : Int,
        val blackhead : Int,
        val acne : Int,
        val sensitive : Int,
        val wrinkle : Int,
        val speckle : Int,
        val black_rim_of_eye : Int
)