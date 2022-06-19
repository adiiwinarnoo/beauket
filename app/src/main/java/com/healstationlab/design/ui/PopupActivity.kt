package com.healstationlab.design.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityPopupAtivityBinding
import com.healstationlab.design.dto.popup
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PopupActivity : AppCompatActivity() {
    lateinit var binding : ActivityPopupAtivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPopupAtivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        when(intent.getIntExtra("pos", 0)){
            in 0..10000 -> {
                getPopup(intent.getIntExtra("pos", 0))
            }
        }

        binding.imageView98.setOnClickListener {
            finish()
        }

        binding.imageView93.setOnClickListener {
            finish()
        }
    }

    private fun getPopup(pos : Int){

        RetrofitMansae.server.getPopup()
                .enqueue(object : Callback<popup> {
                    override fun onFailure(call: Call<popup>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<popup>, response: Response<popup>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                when(response.body()!!.data[pos].contentType){
                                    "TEXT" -> {
                                        binding.textView228.isVisible = true
                                        binding.textView228.text = response.body()!!.data[pos].title
                                    }
                                    "IMAGE" -> {
                                        binding.imageView103.isVisible = true
                                        Glide.with(this@PopupActivity).load(response.body()!!.data[pos].imageUrl).into(binding.imageView103)
                                    }
                                }


                                if(response.body()!!.data[pos].buttonType == "MARK"){
                                    binding.textView224.text = "하루 동안 보지 않음"
                                    binding.imageView92.setOnClickListener {
                                        finish()
                                    }
                                } else {
                                    binding.textView224.text = "상세보기"
                                    binding.imageView92.setOnClickListener {
                                        when(response.body()!!.data[pos].linkType){
                                            "EVENT" -> {
                                                val today : Int = (System.currentTimeMillis() / 1000).toInt()
                                                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)

                                                if(response.body()!!.data[pos].event.endDate < (dateFormat.format(today.toLong()*1000).toString())){
                                                    Toast.makeText(this@PopupActivity, "끝난 이벤트 입니다.", Toast.LENGTH_SHORT).show()
                                                    finish()
                                                } else {
                                                    moveEvent(response.body()!!.data[pos].event.id)
                                                }
                                            }
                                            "EXTERNAL" -> {
                                                    val intent = Intent(Intent.ACTION_VIEW)
                                                    intent.data = Uri.parse(response.body()!!.data[pos].linkUrl)
                                                    startActivity(intent)
                                                    overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                                    finish()

                                            }
                                            "PRODUCT" -> {
                                                moveProduct((response.body()!!.data[pos].product.id))
                                            }
                                        }
                                    }
                                }
                            }

                            else -> {

                            }
                        }
                    }
                })
    }

    fun moveEvent(id : Int){
        val intnet = Intent(this, EventDetailActivity::class.java)
        intnet.putExtra("id", id)
        startActivity(intnet)
        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        finish()
    }

    fun moveProduct(id : Int){
        val intnet = Intent(this, ProductDetailActivity::class.java)
        intnet.putExtra("id", id)
        startActivity(intnet)
        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        finish()
    }
}