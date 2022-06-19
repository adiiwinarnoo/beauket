package com.healstationlab.design.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.PublicNoticeAdapter
import com.healstationlab.design.adapter.FAQAdapter
import com.healstationlab.design.databinding.ActivityPublicNoticeBinding
import com.healstationlab.design.dto.PublicNotice
import com.healstationlab.design.dto.faq
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PublicNoticeActivity : AppCompatActivity() {
    val publicArrayList : ArrayList<PublicNotice.PublicData> = arrayListOf()
    val faqArrayList : ArrayList<faq.Data> = arrayListOf()
    lateinit var binding : ActivityPublicNoticeBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicNoticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.getStringExtra("public") == "public"){
            binding.textView365.text = "공지사항"
            getPublicNotice()
        } else {
            binding.textView365.text = "FAQ"
            getFaq()
        }

        binding.imageView76.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun getPublicNotice(){
        RetrofitMansae.server.getPublicNotice()
            .enqueue(object : Callback<PublicNotice>{
                override fun onFailure(call: Call<PublicNotice>, t: Throwable) {

                }

                override fun onResponse(call: Call<PublicNotice>, response: Response<PublicNotice>) {

                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data){
                                publicArrayList.add(
                                    PublicNotice.PublicData(i.id, i.title, i.contents)
                                )
                            }
                            val publicNoticeAdapter = PublicNoticeAdapter(publicArrayList)
                            binding.publicRecyclerview.apply {
                                adapter = publicNoticeAdapter
                                layoutManager = LinearLayoutManager(this@PublicNoticeActivity)
                            }
                        }
                    }
                }
            })
    }

    private fun getFaq(){
        RetrofitMansae.server.getFaq()
            .enqueue(object : Callback<faq>{
                override fun onFailure(call: Call<faq>, t: Throwable) {

                }

                override fun onResponse(call: Call<faq>, response: Response<faq>) {
                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data){
                                faqArrayList.add(
                                    faq.Data(i.id, i.question, i.answer)
                                )
                            }
                            val faqAdapter = FAQAdapter(faqArrayList)
                            binding.publicRecyclerview.apply {
                                adapter = faqAdapter
                                layoutManager = LinearLayoutManager(this@PublicNoticeActivity)
                            }
                        }
                    }
                }
            })
    }
}