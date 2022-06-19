package com.healstationlab.design.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.healstationlab.design.databinding.ActivityNewPasswordBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewPasswordBinding
    lateinit var split : List<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityNewPasswordBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        split = intent.getStringExtra("str")!!.replace("{","").replace("}","").replace("\"","").replace(",","").replace("DI","").replace("RES_SEQ","").replace("NATIONALINFO","").split(":")

        binding.authButton2.setOnClickListener {
            if(binding.newPasswordText.text.toString().isNotEmpty()){
                if(binding.newPasswordText.text.toString() == binding.newPasswordText2.text.toString()){
                    newPassword()
                } else {
                    Toast.makeText(this, "입력한 비밀번호와 다릅니다!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun newPassword() {
        val body : HashMap<String, String> = HashMap()
        body["contact"] = split[2]
        body["password"] = binding.newPasswordText.text.toString()
        RetrofitMansae.server.newPassword(body)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            Toast.makeText(this@NewPasswordActivity, "비밀번호가 변경되었습니다!", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        "FAIL" -> {
                            Toast.makeText(this@NewPasswordActivity, "검색된 회원이 없습니다!", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this@NewPasswordActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
}