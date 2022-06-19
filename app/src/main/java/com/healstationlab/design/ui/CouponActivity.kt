package com.healstationlab.design.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CouponAdapter
import com.healstationlab.design.databinding.ActivityCouponBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.coupon
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CouponActivity : AppCompatActivity() {
    lateinit var binding : ActivityCouponBinding
    val couponList : ArrayList<coupon.Data.Meta> = arrayListOf()
    val body : HashMap<String, String> = HashMap()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCouponBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getCoupon()

        binding.registerCoupon.setOnClickListener {
            if(binding.couponNumberEdittext.text.toString().isNotEmpty()){
                body["code"] = binding.couponNumberEdittext.text.toString()
                registerCoupon(body)
            } else {
                Toast.makeText(this, "쿠폰번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    fun getCoupon(){
        RetrofitMansae.server.getCoupon()
                .enqueue(object : Callback<coupon>{
                    override fun onFailure(call: Call<coupon>, t: Throwable) {
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(call: Call<coupon>, response: Response<coupon>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                for(i in response.body()!!.data){
                                    couponList.add(
                                            coupon.Data.Meta(
                                                    i.meta.id,
                                                    i.meta.name,
                                                    i.meta.code,
                                                    i.meta.type,
                                                    i.meta.discountPrice,
                                                    i.meta.minimumPrice,
                                                    i.meta.expiredAtFormatted,
                                                    i.meta.expiredDate
                                            )
                                    )
                                }
                                val couponAdapter = CouponAdapter(couponList)
                                couponAdapter.notifyDataSetChanged()
                                binding.couponRecyclerview.apply {
                                    adapter = couponAdapter
                                    layoutManager = LinearLayoutManager(this@CouponActivity)
                                }
                                couponAdapter.setItemClickListner(object : CouponAdapter.ItemClickListener{

                                    override fun onClick(view: View, position: Int, focus: Boolean) {

                                    }
                                })
                            }
                        }
                    }
                })
    }

    private fun registerCoupon(body : HashMap<String, String>){
        RetrofitMansae.server.registerCoupon(body = body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                Toast.makeText(this@CouponActivity, "쿠폰발급완료!", Toast.LENGTH_SHORT).show()
                                couponList.clear()
                                getCoupon()
                            }
                            "FAIL" -> {
                                Toast.makeText(this@CouponActivity, response.body()!!.message, Toast.LENGTH_SHORT).show()
                            }

                            else -> {
                                Toast.makeText(this@CouponActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }
}