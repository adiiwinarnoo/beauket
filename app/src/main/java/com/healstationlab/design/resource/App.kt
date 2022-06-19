package com.healstationlab.design.resource

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.kakao.sdk.common.KakaoSdk


class App : Application() {
    private var instance: App? = null

    companion object {
        lateinit var prefs : MSSharedPreferences
    }

    override fun onCreate() {
        super.onCreate()
        prefs = MSSharedPreferences(applicationContext)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        instance = this
        KakaoSdk.init(this, "8531c12a17718321449db74dca6120a3")
//        val intentBackgroundService = Intent(this, MyFirebaseMessagingService::class.java)
//        startService(intentBackgroundService)
    }
}