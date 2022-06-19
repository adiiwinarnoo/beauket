package com.healstationlab.design.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityLoginBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.ui.MainActivity
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.NiceIdActivity
import com.healstationlab.design.ui.report.ReportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            hideKeyboard()
        }

        binding.forgotText.setOnClickListener {
            val intent = Intent(this, NiceIdActivity::class.java)
            intent.putExtra("check", true)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.textView422.setOnClickListener {
            val intent = Intent(this, NiceIdActivity::class.java)
            intent.putExtra("findId", true)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.loginButton.setOnClickListener {
            if(binding.uidTextview.text.toString().isNotEmpty() && binding.passwordEdit.text.toString().isNotEmpty()){
                login()
            } else {
                Toast.makeText(this, "아이디 혹은 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(){
        val body : HashMap<String, String> = HashMap()
        body["id"] = binding.uidTextview.text.toString()
        body["password"] = binding.passwordEdit.text.toString()
        body["authType"] = "ID"

        RetrofitMansae.server.login(body = body)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {
                            App.prefs.putStringData(Constant.AUTH, response.body()!!.data!!.token)
                            App.prefs.putBooleanData(Constant.autoLogin, true)
                            App.prefs.putStringData(Constant.EMAIL, body["id"])
                            App.prefs.putStringData(Constant.NAME, response.body()!!.data!!.name)
                            App.prefs.putStringData(Constant.NICK_NAME, response.body()!!.data!!.nickname)
                            App.prefs.putStringData(Constant.PASS_WORD, body["password"])
                            App.prefs.putIntData(Constant.UID, response.body()!!.data!!.id)
                            App.prefs.putStringData(Constant.AUTH_TYPE, response.body()!!.data!!.authType)
                            getUser()
                            Log.d("comment", App.prefs.getStringData(Constant.AUTH).toString())

                            /**edit by adi**/
                            if (App.prefs.getBooleanData(Constant.autoLogin)){
                                val intent = Intent(this@LoginActivity, ReportActivity::class.java)
                                Log.d("comment", App.prefs.getStringData(Constant.AUTH).toString())
                                intent.putExtra("status-check",true)
                                startActivity(intent)
                            }else{
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                Log.d("comment", App.prefs.getStringData(Constant.AUTH).toString())
                                intent.putExtra("status-check",false)
                                startActivity(intent)
                            }


                        }

                        "FAIL" -> {
                            Toast.makeText(this@LoginActivity, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
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
                                val rpcsplit = rpc.split("A")
                                val newRPC = rpcsplit[0] + rpcsplit[1].removeRange(0,1)
                                App.prefs.putStringData(Constant.CODE, newRPC)
                            }

                            if(response.body()?.data?.skinType != null){
                                App.prefs.putStringData(Constant.MANSAE_SKIN_TYPE, response.body()?.data?.skinType)
                            }

                            if(response.body()?.data?.skinProblems?.size != 0){
                                App.prefs.putStringData(Constant.SKIN_PROBLEM, response.body()?.data?.skinProblems?.get(0))
                            }
                        }
                    }
                }
            })
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.uidTextview.windowToken, 0)
    }


    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}

