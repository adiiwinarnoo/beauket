package com.healstationlab.design.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.ReviewAdapter
import com.healstationlab.design.databinding.ActivityMyReviewBinding
import com.healstationlab.design.dto.review
import com.healstationlab.design.model.ReviewModel
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyReviewActivity : AppCompatActivity() {
    val reviewList : ArrayList<ReviewModel> = ArrayList()
    lateinit var binding : ActivityMyReviewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyReviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView99.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }

         getMyReview()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun getMyReview(){
        RetrofitMansae.server.getMyReview()
                .enqueue(object : Callback<review>{
                    override fun onFailure(call: Call<review>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<review>, response: Response<review>) {
                        if (response.body()?.data?.isNotEmpty()!!) {
                            for(i in response.body()?.data!!){
                                reviewList.add(
                                        ReviewModel(
                                                content = i.content.toString(),
                                                imageUrl = i.userData.imageUrl,
                                                rating = i.rating,
                                            skinProblems = i.userData.skinProblems as ArrayList<String>?,
                                            nickname = i.userData.nickname,
                                            imageUrls = i.imageUrls as ArrayList<String>,
                                            createdDate = i.createdDate,
                                            birthDate = i.userData.birthDate
                                        )
                                )
                            }
                            binding.myRecyclerview.apply {
                                adapter = ReviewAdapter(reviewList)
                                layoutManager = LinearLayoutManager(this@MyReviewActivity)
                            }
                        }
                    }
                })
    }
}