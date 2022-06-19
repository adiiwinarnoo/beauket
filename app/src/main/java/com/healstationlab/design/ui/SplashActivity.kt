package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySplashBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.login.SelectLoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        Handler(Looper.getMainLooper()).postDelayed({
            getLogin()
        }, 2000)
    }

    private fun loginCheck(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            } else {
                val token = task.result
                // Log and toast

                val body : HashMap<String, String> = HashMap()
                body["pushToken"] = token!!
                body["platform"] = "android"
                pushToken(body)
            }
        })

//            Log.d("auto : ", App.prefs.getBooleanData(Constant.autoLogin).toString())
//            // false일 때
//            val intent = Intent(this, SelectLoginActivity::class.java)
//            startActivity(intent)
//            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//            finish()
    }

    private fun getLogin(){
        loginCheck()
        val body : HashMap<String, String> = HashMap()
        when(App.prefs.getBooleanData(Constant.autoLogin)){
            true -> {
                when(App.prefs.getStringData(Constant.AUTH_TYPE) == "ID"){
                    true -> {
                        body["id"] = App.prefs.getStringData(Constant.EMAIL).toString()
                        body["password"] = App.prefs.getStringData(Constant.PASS_WORD).toString()
                        body["authType"] = "ID"
                    }
                    false -> {
                        body["id"] = App.prefs.getStringData(Constant.AUTH_ID).toString()
                        body["authType"] = App.prefs.getStringData(Constant.AUTH_TYPE).toString()
                    }
                }

                RetrofitMansae.server.login(body = body)
                    .enqueue(object : Callback<auth> {
                        override fun onFailure(call: Call<auth>, t: Throwable) {
                            Toast.makeText(this@SplashActivity, "서버 에러 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show()
                            finish()
                        }

                        override fun onResponse(call: Call<auth>, response: Response<auth>) {
                            when(response.body()?.responseCode){
                                "SUCCESS" -> {
                                    App.prefs.putStringData(Constant.AUTH, response.body()!!.data!!.token)
                                    App.prefs.putBooleanData(Constant.autoLogin, true)
                                    App.prefs.putStringData(Constant.EMAIL, body["id"])
                                    App.prefs.putStringData(Constant.AUTH_TYPE, body["authType"])
                                    App.prefs.putStringData(Constant.NAME, response.body()!!.data!!.name)
                                    App.prefs.putStringData(Constant.NICK_NAME, response.body()!!.data!!.nickname)
                                    App.prefs.putIntData(Constant.UID, response.body()!!.data!!.id)
                                    getUser()

                                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                    finish()
                                }

                                "FAIL" -> {
                                    Toast.makeText(this@SplashActivity, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                                    App.prefs.putBooleanData(Constant.autoLogin, false)
                                    val intent = Intent(this@SplashActivity, SelectLoginActivity::class.java)
                                    startActivity(intent)
                                    overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                    finish()
                                }

                                else -> {
                                    Toast.makeText(this@SplashActivity, "서버 점검중입니다. : ${response.body().toString()}", Toast.LENGTH_SHORT).show()
                                    //finish()
                                }
                            }
                        }
                    })
            }
            false -> {
                val intent = Intent(this, WelcomeScreenActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                finish()
            }
        }
    }

    fun getUser(){
        RetrofitMansae.server.getUserInfo()
            .enqueue(object : Callback<userInfo>{
                override fun onFailure(call: Call<userInfo>, t: Throwable) {

                }

                override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            Constant.meituCheck = response.body()?.data!!.analysisData
                            if(response.body()?.data?.recommendProductCode != null){
                                val rpc = response.body()?.data?.recommendProductCode.toString()
                                val rpcsplit = rpc.split("A") // O7S7P2
                                val newRPC = rpcsplit[0] + rpcsplit[1].removeRange(0,1)
                                App.prefs.putStringData(Constant.CODE, newRPC)
                            }
                            if(response.body()?.data?.skinType != null){
                                App.prefs.putStringData(Constant.MANSAE_SKIN_TYPE, response.body()?.data?.skinType)
                            }
                            if(response.body()?.data?.skinProblems!!.isNotEmpty()){
                                App.prefs.putStringData(Constant.SKIN_PROBLEM, response.body()?.data?.skinProblems?.get(0))
                            }
                            App.prefs.putStringData(Constant.NAME, response.body()?.data?.name.toString())
                            App.prefs.putStringData(Constant.EMAIL, response.body()?.data?.loginId.toString())
                            App.prefs.putStringData(Constant.PHONE, response.body()?.data?.contact.toString())
                            App.prefs.putStringData(Constant.BIRTH, response.body()?.data?.birthDate.toString())
                        }
                    }
                }
            })
    }
//    private fun getHashKey() {
//        var packageInfo: PackageInfo? = null
//        try {
//            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
//        } catch (e: PackageManager.NameNotFoundException) {
//            e.printStackTrace()
//        }
//        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
//        for (signature in packageInfo!!.signatures) {
//            try {
//                val md: MessageDigest = MessageDigest.getInstance("SHA")
//                md.update(signature.toByteArray())
//
//            } catch (e: NoSuchAlgorithmException) {
//
//            }
//        }
//    }

    private fun pushToken(body: HashMap<String, String>){

        RetrofitMansae.server.pushToken(body = body)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {

                        }
                    }
                }
            })
    }

}