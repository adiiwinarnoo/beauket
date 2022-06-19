package com.healstationlab.design.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityProductInqueryBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Response

class ProductInqueryActivity : AppCompatActivity() {
    var id = 0
    lateinit var binding : ActivityProductInqueryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductInqueryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            hideKeyboard()
        }

        Glide.with(this).load(intent.getStringExtra("url")).into(binding.imageView75)
        binding.textView334.text = "${intent.getStringExtra("brand")}"
        binding.textView335.text = "${intent.getStringExtra("name")}"
        binding.textView336.text = "${intent.getStringExtra("price")}"
        id = intent.getIntExtra("id", 0)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.inqueryButton.setOnClickListener {
            val body : HashMap<String, String> = HashMap()
            body["content"] = binding.inqueryEdittext.text.toString()
            postInquery(body)
        }

        binding.inqueryEdittext.addTextChangedListener {
            binding.editCount.text = binding.inqueryEdittext.text.length.toString()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun postInquery(body : HashMap<String, String>){
        RetrofitMansae.server.postInquery(body = body, id = id)
            .enqueue(object : retrofit2.Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            App.prefs.putIntData(Constant.PRODUCT_QUERY_ID, id)
                            Toast.makeText(this@ProductInqueryActivity, "문의를 등록했습니다.", Toast.LENGTH_SHORT).show()
                            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                            finish()
                        }

                        else -> {
                            Toast.makeText(this@ProductInqueryActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.inqueryEdittext.windowToken, 0)
    }
}