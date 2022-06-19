package com.healstationlab.design.model

data class Inquery (
        val type : String? = null,
        val content : String,
        val answered : Boolean,
        val name : String? = null,
        val id : Int,
        var reply : String ="test",
        var createdDate : String? = null,
        var createdAt : List<Int>? = null
)