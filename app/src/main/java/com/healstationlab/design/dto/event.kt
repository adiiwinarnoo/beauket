package com.healstationlab.design.dto

data class event(
    val responseCode : String,
    val message : String,
    val data : List<EventData>
)

data class EventData(
    val id : Int,
    val name : String,
    val imageUrl : String,
    val details : String,
    val endDate : String,
    val startDate : String
)