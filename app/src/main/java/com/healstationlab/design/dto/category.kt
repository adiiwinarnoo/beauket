package com.healstationlab.design.dto

class category (
        val responseCode : String,
        val message : String,
        val data : List<CategoryData>
)

data class CategoryData(
        val id : Int?,
        val name : String?,
        val iconImageUrl : String? = ""
//        val level : Int
//        val parent : Parent
)

//data class Parent(
//        val id : Int,
//        val name : String,
//        val
//)