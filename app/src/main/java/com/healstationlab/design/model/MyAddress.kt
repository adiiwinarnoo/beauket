package com.healstationlab.design.model

import com.healstationlab.design.dto.myAddress

data class MyAddress(
        val id: Int,
        val name: String,
        val contact: String,
        val email: String,
        val addressCode: String,
        val address: String,
        val addressDetail: String,
        val defaultAddress: Boolean
)