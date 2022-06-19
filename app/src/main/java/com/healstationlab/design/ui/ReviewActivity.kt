package com.healstationlab.design.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.ReviewAdapter
import com.healstationlab.design.databinding.ActivityReviewBinding
import com.healstationlab.design.dto.review
import com.healstationlab.design.model.ReviewModel
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReviewActivity : AppCompatActivity() {
    lateinit var binding : ActivityReviewBinding
    val reviewList : ArrayList<ReviewModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReviewBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView210.text = intent.getStringExtra("total") // 리뷰 total 별점
        binding.ratingBar.rating = intent.getStringExtra("total")!!.toFloat() // 리뷰 레이팅
        binding.progressBar6.progress = intent.getIntExtra("5", 0)
        binding.progressBar7.progress = intent.getIntExtra("4", 0)
        binding.progressBar8.progress = intent.getIntExtra("3", 0)
        binding.progressBar9.progress = intent.getIntExtra("2", 0)
        binding.progressBar10.progress = intent.getIntExtra("1", 0)
        binding.textView216.text = intent.getIntExtra("5", 0).toString()
        getProductReview()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun getProductReview(){
        RetrofitMansae.server.getReView(id = intent.getIntExtra("id", 0), pageSize = 50)
                .enqueue(object : Callback<review> {
                    override fun onFailure(call: Call<review>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<review>, response: Response<review>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                binding.textView208.text = response.body()!!.totalCount.toString() // 리뷰 개수

                                for(i in response.body()?.data!!){
                                    reviewList.add(
                                            ReviewModel(
                                                    content = i.content.toString(),
                                                    imageUrl = i.userData.imageUrl,
                                                    rating = i.rating,
                                                    nickname = i.userData.nickname,
                                                    skinProblems = i.userData.skinProblems as ArrayList<String>,
                                                    imageUrls = i.imageUrls as ArrayList<String>,
                                                    createdDate = i.createdDate,
                                                birthDate = i.userData.birthDate
                                            )
                                    )
                                }

                                binding.reviewRecyclerview.apply {
                                    adapter = ReviewAdapter(reviewList)
                                    layoutManager = LinearLayoutManager(this@ReviewActivity)
                                }
                            }

                            else -> {
                                Toast.makeText(this@ReviewActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }
}