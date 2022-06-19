package com.healstationlab.design.resource

import com.healstationlab.design.resource.service.UserInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitMansae {
    private val retrofit = Retrofit.Builder()
        .baseUrl(Constant.BASE_URL_MANSAE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val server: UserInterface = retrofit.create(UserInterface::class.java)
}