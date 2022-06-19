package com.healstationlab.design.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityFindIdResultBinding
import com.healstationlab.design.dto.findIdDTO
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FindIdResultActivity : AppCompatActivity() {
    lateinit var binding : ActivityFindIdResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val str = intent.getStringExtra("str")
        val split =  str!!.split(",")
        val contact = split[1].replace("\"", "").replace("MOBILE_NO", "").replace(":","")


        binding.imageView136.setOnClickListener {
            val intent = Intent(this, SelectLoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        binding.textView426.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        getId(contact)
    }

    private fun getId(contact : String){
        RetrofitMansae.server.getId(contact = contact)
            .enqueue(object : Callback<findIdDTO>{
                override fun onResponse(call: Call<findIdDTO>, response: Response<findIdDTO>) {

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            binding.textView425.text = response.body()!!.data
                        }

                        "FAIL" -> {
                            binding.textView423.text = response.body()!!.message
                        }
                    }
                }

                override fun onFailure(call: Call<findIdDTO>, t: Throwable) {
                    Toast.makeText(this@FindIdResultActivity, "서버에러", Toast.LENGTH_SHORT).show()
                }
            })
    }
}