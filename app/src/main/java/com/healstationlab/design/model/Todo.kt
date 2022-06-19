package com.healstationlab.design.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo (
    @PrimaryKey(autoGenerate = true) var id : Int,
    var skin_age : String,
    var skin_type : String,
    var skin_color : String,
    var color_code : String,
    var t_score : Int,
    var cheek_score : Int,
    var chin_score : Int,
    var slsd_score : Int,
    var pfpz_score : Int,
    var hssdx_score : Int
)