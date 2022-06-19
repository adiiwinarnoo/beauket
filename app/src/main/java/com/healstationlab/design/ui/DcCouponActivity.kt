package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CouponAdapter
import com.healstationlab.design.databinding.ActivityDcCouponBinding
import com.healstationlab.design.dto.coupon
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DcCouponActivity : AppCompatActivity() {

    val couponList : ArrayList<coupon.Data.Meta> = arrayListOf()
    var price : Long = 0

    lateinit var binding : ActivityDcCouponBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDcCouponBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.imageView22.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }

        price = intent.getLongExtra("price", 0)
        getCoupon()
    }

    private fun getCoupon(){
        RetrofitMansae.server.getCoupon()
                .enqueue(object : Callback<coupon> {
                    override fun onFailure(call: Call<coupon>, t: Throwable) {

                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(call: Call<coupon>, response: Response<coupon>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                for(i in response.body()!!.data){
                                        couponList.add(
                                            coupon.Data.Meta(
                                                i.id,
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
                                val couponAdapter = CouponAdapter(couponList, price = price)
                                couponAdapter.notifyDataSetChanged()
                                binding.couponRecyclerView.apply {
                                    adapter = couponAdapter
                                    layoutManager = LinearLayoutManager(this@DcCouponActivity)
                                }

                                couponAdapter.setItemClickListner(object : CouponAdapter.ItemClickListener{
                                    override fun onClick(view: View, position: Int, focus: Boolean) {
                                        if(focus){
                                            Toast.makeText(this@DcCouponActivity, "사용할 수 없는 쿠폰입니다.", Toast.LENGTH_SHORT).show()
                                        } else {
                                            val intent = Intent()
                                            intent.putExtra("sale", couponList[position].discountPrice)
                                            intent.putExtra("id", couponList[position].id)
                                            setResult(Activity.RESULT_OK, intent)
                                            finish()
                                        }
                                    }
                                })
                            }
                        }
                    }
                })
    }
}