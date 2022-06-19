package com.healstationlab.design.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityPostInqueryBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PostInqueryActivity : AppCompatActivity() {
    lateinit var binding : ActivityPostInqueryBinding
    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostInqueryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView109.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }

        binding.editTextTextPersonName11.addTextChangedListener {
            binding.textView379.text = binding.editTextTextPersonName11.text.toString().length.toString()
        }

        /**문의하기**/
        binding.textView378.setOnClickListener {
            val body : HashMap<String, String> = HashMap()
            body["content"] = binding.editTextTextPersonName11.text.toString()
            body["type"] = type
            postInquery(body)
        }

        val spinnerItems = arrayListOf("뷰켓 쇼핑 문의", "피부분석 문의", "이벤트 문의", "서비스 불편, 오류 제보", "사용방법, 기타 문의", "아이디어 제안, 칭찬", "제휴 문의")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.spinner4.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    when(position){
                        0 -> type ="SHOPPING"
                        1 -> type ="SKIN"
                        2 -> type ="EVENT"
                        3 -> type ="SERVICE"
                        4 -> type ="ETC"
                        5 -> type ="PROPOSAL"
                        6 -> type ="AFFILIATE"
                    }
                }
            }
        }
        binding.textView376.setOnClickListener {
            val intnet = Intent(this, PublicNoticeActivity::class.java)
            intent.putExtra("public", "faq")
            startActivity(intnet)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }
    }

    private fun postInquery(body : HashMap<String, String>){
        RetrofitMansae.server.postInquery(body = body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                Toast.makeText(this@PostInqueryActivity, "문의등록완료!", Toast.LENGTH_SHORT).show()
                                setResult(Activity.RESULT_OK)
                                finish()
                            }
                        }
                    }
                })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}