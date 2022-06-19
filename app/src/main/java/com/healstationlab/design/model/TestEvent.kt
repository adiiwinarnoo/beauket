package com.healstationlab.design.model

data class TestEvent(
        val id : Int,
        val imageUrl : String,
        val name : String,
        val details : String,
        val startDate : String?,
        val endDate : String?
)