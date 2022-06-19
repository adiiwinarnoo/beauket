package com.healstationlab.design.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySNSBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.kakao
import com.healstationlab.design.dto.reportMeitu
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.resource.service.UserInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.regex.Pattern

class SNSActivity : AppCompatActivity() {
    lateinit var binding : ActivitySNSBinding
    lateinit var split : List<String>
    var nickNameCheck = false
    var sns = ""
    var id = ""
    var email : String? = ""
    var cuurentNickName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySNSBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView408.setOnClickListener {
            nickNameCheck()
        }

        split = intent.getStringExtra("str")!!.replace("{","").replace("}","").replace("\"","").replace(",","").replace("DI","").replace("RES_SEQ","").replace("NATIONALINFO","").split(":")


        sns = intent.getStringExtra("SNS").toString()
        if(sns == "KAKAO"){
            kakao(Constant.kakaoToken)
        } else {
            id = intent.getStringExtra("id").toString()
            email = intent.getStringExtra("email").toString()
            binding.editTextTextPersonName4.setText(email)
        }

        binding.textView410.setOnClickListener {
            if(binding.editTextTextPersonName4.text.toString().isNotEmpty()){
                val emailReg : Pattern = android.util.Patterns.EMAIL_ADDRESS
                when(emailReg.matcher(binding.editTextTextPersonName4.text.toString()).matches()) {
                    true -> {
                        if(nickNameCheck && cuurentNickName == binding.editTextTextPersonName13.text.toString()){
                            signUp()
                        } else {
                            Toast.makeText(this, "닉네임을 확인해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                    false -> {
                        if(binding.editTextTextPersonName4.text.toString().contains(" ")){
                            Toast.makeText(this, "아이디에 공백을 지워주세요!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "이메일 형식으로 입력해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun kakao(token : String){

        val retrofit = Retrofit.Builder()
                .baseUrl(Constant.KAKAO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val server: UserInterface = retrofit.create(UserInterface::class.java)
        server.getInfo(Authorization = "Bearer $token")
                .enqueue(object : Callback<kakao> {
                    override fun onFailure(call: Call<kakao>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<kakao>, response: Response<kakao>) {
                        when(response.code()){
                            200 -> {
                                id = response.body()!!.id.toString() // third
                                if(email != null){
                                    binding.editTextTextPersonName4.setText(email)
                                }
                                email = response.body()?.kakao_account?.email.toString()
                            }
                            else -> {
                                Toast.makeText(this@SNSActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    private fun signUp(){
        var gender = ""
        gender = if(split[6]=="1"){
            "MAN"
        } else {
            "WOMAN"
        }
        val body : HashMap<String, String?> = HashMap()
        body["id"] = id
        body["email"] = binding.editTextTextPersonName4.text.toString()
        body["cellphone"] = split[2]
        body["name"] = split[11]
        body["nickname"] = binding.editTextTextPersonName13.text.toString()
        body["birthDateParam"] = split[8]
        body["gender"] = gender
        body["authType"] = sns // sns


        RetrofitMansae.server.signUp(body = body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                App.prefs.putStringData(Constant.AUTH_TYPE, sns)
                                App.prefs.putStringData(Constant.AUTH_ID, body["id"])
                                App.prefs.putStringData(Constant.NAME, body["name"])
                                App.prefs.putStringData(Constant.EMAIL, body["email"])
                                App.prefs.putStringData(Constant.NICK_NAME, body["nickname"])
                                App.prefs.putStringData(Constant.NAME, body["name"])
                                App.prefs.putStringData(Constant.GENDER, body["gender"])
                                App.prefs.putStringData(Constant.BIRTH, body["birthDateParam"])
                                App.prefs.putStringData(Constant.PHONE, body["cellphone"])

                                login()

                                Toast.makeText(this@SNSActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@SNSActivity, PermissionActivity::class.java)
                                intent.putExtra("type", sns)
                                startActivity(intent)
                                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                finish()
                            }

                            "FAIL" -> {

                                 Toast.makeText(this@SNSActivity, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                 Toast.makeText(this@SNSActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    private fun nickNameCheck(){
        if(binding.editTextTextPersonName13.text.toString() != ""){
            RetrofitMansae.server.nicknameCheckNoHeader(binding.editTextTextPersonName13.text.toString())
                    .enqueue(object : Callback<auth>{
                        override fun onFailure(call: Call<auth>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<auth>, response: Response<auth>) {
                            when(response.body()?.responseCode){
                                "SUCCESS" -> {
                                    Toast.makeText(this@SNSActivity, "사용 가능한 닉네임 입니다!", Toast.LENGTH_SHORT).show()
                                    nickNameCheck = true
                                    cuurentNickName = binding.editTextTextPersonName13.text.toString()
                                }

                                "FAIL" -> {
                                    nickNameCheck = false
                                    Toast.makeText(this@SNSActivity, "이미 사용중인 닉네임입니다!", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
        } else {
            Toast.makeText(this, "닉네임을 확인해주세요!", Toast.LENGTH_SHORT).show()
        }
    }

    fun login(){
        val body : HashMap<String, String> = HashMap()
        body["id"] = App.prefs.getStringData(Constant.AUTH_ID).toString()
        body["authType"] = App.prefs.getStringData(Constant.AUTH_TYPE).toString()

        RetrofitMansae.server.login(body = body)
                .enqueue(object : Callback<auth> {
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                App.prefs.putBooleanData(Constant.autoLogin, true)
                                App.prefs.putStringData(Constant.AUTH, response.body()!!.data!!.token)
                                App.prefs.putIntData(Constant.UID, response.body()!!.data!!.id)
                                getMeitu()
                            }

                            "FAIL" -> {
                                Toast.makeText(this@SNSActivity, "정보 가져오기 실패", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(this@SNSActivity, "서버 에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun getMeitu(){
        RetrofitMansae.server.getMansaeMeituReport()
                .enqueue(object : Callback<reportMeitu>{
                    override fun onFailure(call: Call<reportMeitu>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<reportMeitu>, response: Response<reportMeitu>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                if(response.body()?.data == null){
                                    App.prefs.putBooleanData(Constant.MEITU_DATA_CHECK, false)
                                } else {
                                    App.prefs.putBooleanData(Constant.MEITU_DATA_CHECK, true)
                                    App.prefs.putStringData(Constant.STORE_NAME,
                                        response.body()!!.data[0].store_name
                                    )
                                }
                            }
                        }
                    }
                })
    }
}