package com.healstationlab.design.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityEventDetailBinding
import com.healstationlab.design.dto.event
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityEventDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getEvent()

        binding.imageView76.setOnClickListener {
            onBackPressed()
        }
        
        binding.goList.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun getEvent(){
        binding.webview.settings.javaScriptEnabled = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.loadWithOverviewMode = true
        binding.webview.settings.useWideViewPort = true
        binding.webview.settings.builtInZoomControls = true
        binding.webview.settings.setSupportZoom(true)
        binding.webview.settings.displayZoomControls = false

        val id = intent.getIntExtra("id", 0)

        RetrofitMansae.server.getEvent()
                .enqueue(object : Callback<event>{
                    override fun onFailure(call: Call<event>, t: Throwable) {
                    }

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<event>, response: Response<event>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                for(i in response.body()!!.data){
                                    if(i.id == id){
                                        binding.webview.loadData(i.details.replace("860", "1280"), "text/html", "UTF-8")
                                        binding.textView346.text = i.name
                                        binding.textView358.text = "${i.startDate} ~ ${i.endDate}"
                                        Glide.with(this@EventDetailActivity).
                                        load(i.imageUrl).apply(
                                                RequestOptions.centerInsideTransform().transform(
                                                        CenterCrop(), GranularRoundedCorners(8f, 8f,0f,0f)
                                                ))
                                                .into(binding.cardImg)
                                        break
                                    }
                                }
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