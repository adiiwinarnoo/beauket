package com.healstationlab.design.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.AddressAdapter
import com.healstationlab.design.model.Address
import com.healstationlab.design.databinding.ActivitySearchAddressBinding
import com.healstationlab.design.dto.address
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.service.UserInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchAddressActivity : AppCompatActivity() {

    lateinit var binding : ActivitySearchAddressBinding
    val addrArrayList : ArrayList<Address> = arrayListOf()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Constant.ADDRESS_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val server: UserInterface = retrofit.create(UserInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySearchAddressBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            hideKeyboard()
        }

        binding.searchButton.setOnClickListener {
            getAddr()
        }
    }

    private fun getAddr(){
        if(binding.addressEdit.text.toString().length >= 2){
            server.getAddress(keyword = binding.addressEdit.text.toString())
                .enqueue(object : Callback<address>{
                    override fun onFailure(call: Call<address>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<address>, response: Response<address>) {
                        addrArrayList.clear()
                        if(response.body()?.results?.juso == null){
                            Toast.makeText(this@SearchAddressActivity, "주소를 상세히 입력해주세요!", Toast.LENGTH_SHORT).show()
                        } else {
                            for(i in response.body()!!.results!!.juso){
                                addrArrayList.add(Address(i.zipNo, i.roadAddr, i.jibunAddr))
                            }
                        }
                        val addressAdatper = AddressAdapter(addrArrayList)
                        binding.addrRecyclerview.apply {
                            adapter = addressAdatper
                            layoutManager = LinearLayoutManager(this@SearchAddressActivity)
                        }

                        addressAdatper.setItemClickListner(object : AddressAdapter.ItemClickListener{
                            override fun onClick(view: View, position: Int) {
                                val intent = Intent()
                                intent.putExtra("zipNo", addrArrayList[position].zipNo)
                                intent.putExtra("roadAddr", addrArrayList[position].roadAddr)
                                setResult(Activity.RESULT_OK, intent)
                                onBackPressed()
                            }
                        })
                    }
                })

        } else {
            Toast.makeText(this, "주소를 2글자 이상 입력해주세요", Toast.LENGTH_SHORT).show()
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.addressEdit.windowToken, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}