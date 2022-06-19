package com.healstationlab.design.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.AlarmAdapter
import com.healstationlab.design.databinding.ActivityAlarmBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.model.AlarmModel
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmActivity : AppCompatActivity() {
    lateinit var binding : ActivityAlarmBinding
    var alarmList : ArrayList<AlarmModel> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.fade_out)
        }
        getUser()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.fade_out)
    }

    private fun getUser(){
        RetrofitMansae.server.getUserInfo()
                .enqueue(object : Callback<userInfo> {
                    override fun onFailure(call: Call<userInfo>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                alarmList = arrayListOf(
                                        AlarmModel(title = "뷰켓 이벤트", state = response.body()!!.data.alarmEventOn),
                                        AlarmModel(title = "뷰켓 공지사항", state = response.body()!!.data.alarmNoticeOn),
                                        AlarmModel(title = "주문상태 알림", state = response.body()!!.data.alarmOrderStatusOn),
                                        AlarmModel(title = "상품 문의 알림", state = response.body()!!.data.alarmProductInquiryOn),
                                        AlarmModel(title = "정보성 수신 알림", state = response.body()!!.data.alarmInfoOn)
                                )
                                val alarmAdapter = AlarmAdapter(alarmList)
                                binding.recyclerview.apply {
                                    adapter = alarmAdapter
                                    layoutManager = LinearLayoutManager(this@AlarmActivity)
                                }

                                alarmAdapter.setItemClickListner(object : AlarmAdapter.ItemClickListener{
                                    @SuppressLint("NotifyDataSetChanged")
                                    override fun onClick(state: Boolean, position: Int) {
                                        alarmList[position].state = state
                                        alarmAdapter.notifyDataSetChanged()
                                        putAlarm(alarmList[0].state, alarmList[1].state, alarmList[2].state, alarmList[3].state, alarmList[4].state)
                                    }
                                })

                            }
                            else -> {
                                Toast.makeText(this@AlarmActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun putAlarm(alarmEventOn : Boolean, alarmNoticeOn : Boolean, alarmOrderStatusOn : Boolean, alarmProductInquiryOn : Boolean, alarmInfoOn : Boolean){
        RetrofitMansae.server.putAlarm(alarmEventOn = alarmEventOn, alarmInfoOn = alarmInfoOn, alarmOrderStatusOn = alarmOrderStatusOn, alarmProductInquiryOn = alarmProductInquiryOn, alarmNoticeOn = alarmNoticeOn)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {

                    }
                })
    }
}