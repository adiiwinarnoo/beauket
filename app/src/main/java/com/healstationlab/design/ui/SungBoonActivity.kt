package com.healstationlab.design.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.SungBoonAdapter
import com.healstationlab.design.databinding.ActivitySungBoonBinding
import com.healstationlab.design.dto.detailProduct
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SungBoonActivity : AppCompatActivity() {
    lateinit var binding : ActivitySungBoonBinding
    var list : ArrayList<String> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySungBoonBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView37.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.fade_out)
        }

        binding.background.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.fade_out)
        }

        getDetail(intent.getIntExtra("id", 0))
    }

    private fun getDetail(id : Int){
        RetrofitMansae.server.getProductDetail(id = id)
            .enqueue(object : Callback<detailProduct> {
                override fun onFailure(call: Call<detailProduct>, t: Throwable) {

                }

                override fun onResponse(call: Call<detailProduct>, response: Response<detailProduct>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()?.data?.recommendedIngredients!!){
                                list.add(i.name)
                            }

                            binding.recyclerview.apply {
                                adapter = SungBoonAdapter(list)
                                layoutManager = LinearLayoutManager(this@SungBoonActivity)
                            }
                        }
                        else -> {
                            Toast.makeText(this@SungBoonActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.fade_out)
    }
}