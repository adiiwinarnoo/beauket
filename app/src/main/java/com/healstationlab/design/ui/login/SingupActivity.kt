package com.healstationlab.design.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySingupBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class SingupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingupBinding
    lateinit var split : List<String>
    var nickNameCheck = false
    var cuurentNickName = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            hideKeyboard()
        }
//
//        binding.button.setOnClickListener {
//            val intent = Intent(this, NiceIdActivity::class.java)
//            startActivity(intent)
//        }

//        binding.button.setOnClickListener {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.data = Uri.parse("http://15.165.161.27/view/certification")
//            startActivity(intent)
//        }

        binding.textView94.setOnClickListener {
            nickNameCheck()
        }

        split = intent.getStringExtra("str")!!.replace("{","").replace("}","").replace("\"","").replace(",","").replace("DI","").replace("RES_SEQ","").replace("NATIONALINFO","").split(":")

        binding.editTextTextPersonName10.text = split[2]
        binding.textView367.text = split[11] // 이름
        binding.textView388.text = "${split[8].substring(0..3)}-${split[8].substring(4..5)}-${split[8].substring(6..7)}" // 생년월일


        binding.signPasschkEdit.addTextChangedListener {
            if(binding.signPassEdit.text.toString() == binding.signPasschkEdit.text.toString()){
                binding.confirmTextview.setBackgroundResource(R.color.main_theme)
                binding.confirmTextview.setTextColor(Color.WHITE)

            } else {
                binding.confirmTextview.setBackgroundResource(R.color.button_unselect)
                binding.confirmTextview.setTextColor(Color.parseColor("#727272"))
            }
        }

        /** 뒤로가기 **/
        binding.backButton.setOnClickListener {
            onBackPressed()
        }


        /** 회원가입 **/
        binding.confirmTextview.setOnClickListener {
            if(binding.signIdEdit.text.toString().isNotEmpty() && binding.signPassEdit.text.toString().isNotEmpty()){
                val emailReg : Pattern = android.util.Patterns.EMAIL_ADDRESS
                when(emailReg.matcher(binding.signIdEdit.text.toString()).matches()){
                    true -> {
                        val passwordReg = Regex("^(?=.*[a-zA-Z0-9])(?=.*[a-zA-Z!@#\$%^&*])(?=.*[0-9!@#\$%^&*]).{8,15}\$")
                        when(passwordReg.matches(binding.signPassEdit.text.toString())){
                            true -> {
                                if(binding.signPassEdit.text.toString() == binding.signPasschkEdit.text.toString()){
                                    if(nickNameCheck && cuurentNickName == binding.nickNameEdit.text.toString()){
                                        signUp()
                                    } else {
                                        Toast.makeText(this, "닉네임 중복체크를 해주세요!", Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    Toast.makeText(this, "비밀번호를 확인해주세요!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            false -> { Toast.makeText(this, "영문 숫자조합 8자리 이상 입력해주세요.", Toast.LENGTH_SHORT).show() }
                        }
                    }
                    false -> {
                        if(binding.signIdEdit.text.toString().contains(" ")){
                            Toast.makeText(this, "아이디에 공백을 지워주세요!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "이메일 형식으로 입력해주세요!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                Toast.makeText(this, "아이디 및 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun signUp(){
        var gender = ""
        gender = if(split[6]=="1"){
            "MAN"
        } else {
            "WOMAN"
        }
        val body : HashMap<String, String?> = HashMap()
        body["id"] = binding.signIdEdit.text.toString()
        body["password"] = binding.signPassEdit.text.toString()
        body["cellphone"] = binding.editTextTextPersonName10.text.toString()
        body["nickname"] = binding.nickNameEdit.text.toString()
        body["name"] = binding.textView367.text.toString()
        body["birthDateParam"] = split[8]
        body["gender"] = gender
        body["authType"] = "ID"

        RetrofitMansae.server.signUp(body = body)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {
                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            App.prefs.putStringData(Constant.AUTH_TYPE, "ID")
                            App.prefs.putStringData(Constant.EMAIL, body["id"])
                            App.prefs.putStringData(Constant.PASS_WORD, body["password"])
                            App.prefs.putStringData(Constant.NICK_NAME, body["nickname"])
                            App.prefs.putStringData(Constant.NAME, body["name"])
                            App.prefs.putStringData(Constant.GENDER, body["gender"])
                            App.prefs.putStringData(Constant.BIRTH, binding.textView388.text.toString())
                            App.prefs.putStringData(Constant.PHONE, body["cellphone"])
                            Toast.makeText(this@SingupActivity, "회원가입이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@SingupActivity, PermissionActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                            finish()
                        }

                        "FAIL" -> {
                            Toast.makeText(this@SingupActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(this@SingupActivity, response.body().toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    private fun nickNameCheck(){
        if(binding.nickNameEdit.text.toString() != ""){
            RetrofitMansae.server.nicknameCheckNoHeader(binding.nickNameEdit.text.toString())
                    .enqueue(object : Callback<auth>{
                        override fun onFailure(call: Call<auth>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<auth>, response: Response<auth>) {
                            when(response.body()?.responseCode){
                                "SUCCESS" -> {
                                    Toast.makeText(this@SingupActivity, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                                    cuurentNickName = binding.nickNameEdit.text.toString()
                                    nickNameCheck = true
                                }

                                "FAIL" -> {
                                    nickNameCheck = false
                                    Toast.makeText(this@SingupActivity, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    })
        } else {
            Toast.makeText(this, "닉네임을 확인해주세요!", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.signIdEdit.windowToken, 0)
    }
}