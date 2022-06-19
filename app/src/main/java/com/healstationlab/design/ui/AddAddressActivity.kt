package com.healstationlab.design.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityAddAddressBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddAddressActivity : AppCompatActivity() {
    lateinit var binding : ActivityAddAddressBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.back.setOnClickListener {
            hideKeyboard()
        }

        binding.searchAddress.setOnClickListener { moveAddress() }

        binding.addressText.setOnClickListener { moveAddress() }

        /** 등록 **/
        binding.textView297.setOnClickListener {
            if(binding.name.text.toString().isNotEmpty() && binding.phone.text.toString().isNotEmpty() && binding.phone.text.toString().isNotEmpty()){
                val body : HashMap<String, Any> = HashMap()
                body["name"] = binding.name.text.toString()
                body["contact"] = binding.phone.text.toString()
                body["email"] = App.prefs.getStringData(Constant.EMAIL).toString()
                body["addressCode"] = binding.addressText.text.toString()
                body["address"] = binding.addressRoadText.text.toString()
                body["addressDetail"] = binding.detail.text.toString()
                body["defaultAddress"] = false
                addEditAddress(body)
            } else {
                Toast.makeText(this, "정보를 다 채워주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.checkBox7.setOnCheckedChangeListener { _, b ->
            when(b){
                true -> {
                    binding.name.setText(App.prefs.getStringData(Constant.NAME).toString())
                    binding.phone.setText(App.prefs.getStringData(Constant.PHONE).toString())
                }
                false -> {
                    binding.name.setText("")
                    binding.phone.setText("")
                }
            }
        }

        binding.backButton.setOnClickListener { onBackPressed() }


    }

    private fun moveAddress(){
        val intent = Intent(this, SearchAddressActivity::class.java)
        startActivityForResult(intent, 1000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                1000 -> {
                    binding.addressText.text = data!!.getStringExtra("zipNo")
                    binding.addressRoadText.text = data.getStringExtra("roadAddr")
                }
                else -> {

                }
            }
        }
    }

    private fun addEditAddress(body : HashMap<String, Any>){
        RetrofitMansae.server.addEditAddress(body = body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {

                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                Toast.makeText(this@AddAddressActivity, "배송지가 추가되었습니다.", Toast.LENGTH_SHORT).show()
                                setResult(Activity.RESULT_OK)
                                finish()
                            }

                            else -> {
                                Toast.makeText(this@AddAddressActivity, "서버오류", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
                })
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.detail.windowToken, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}