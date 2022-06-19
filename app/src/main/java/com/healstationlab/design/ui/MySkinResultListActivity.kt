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
import com.healstationlab.design.adapter.ResultListAdapter
import com.healstationlab.design.databinding.ActivityMySkinResultListBinding
import com.healstationlab.design.dto.reportMeitu
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MySkinResultListActivity : AppCompatActivity() {
    var resultList = ArrayList<String>()

    lateinit var binding : ActivityMySkinResultListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMySkinResultListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.background.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.fade_out)
        }
        getResultList()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.fade_out)
    }

    private fun getResultList(){
        RetrofitMansae.server.getMansaeMeituReport()
            .enqueue(object : Callback<reportMeitu>{
                override fun onFailure(call: Call<reportMeitu>, t: Throwable) {

                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<reportMeitu>, response: Response<reportMeitu>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA)

                            for(i in response.body()!!.data){
                                resultList.add(dateFormat.format(Date(i.created_at.toLong()*1000)))
                            }

                            binding.textView123.text = resultList.size.toString()+"건" // 검사결과 size

                            val resultListAdapter = ResultListAdapter(resultList)
                            binding.resultListRecyclerview.apply {
                                adapter = resultListAdapter
                                layoutManager = LinearLayoutManager(this@MySkinResultListActivity)
                            }

                            resultListAdapter.setItemClickListner(object : ResultListAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent()
                                    binding.background
                                    intent.putExtra("position", position)
                                    setResult(Activity.RESULT_OK, intent)
                                    finish()
                                    overridePendingTransition(0, R.xml.fade_out)
                                }
                            })
                        }

                        else -> {
                            Toast.makeText(this@MySkinResultListActivity, "서버 에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
}