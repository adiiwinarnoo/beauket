package com.healstationlab.design.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.DeliveryAdapter
import com.healstationlab.design.databinding.ActivityDeliveryListBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.myAddress
import com.healstationlab.design.model.MyAddress
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryListActivity : AppCompatActivity() {

    lateinit var binding : ActivityDeliveryListBinding
    var addressArrayList : ArrayList<MyAddress> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDeliveryListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.addDeliveryButton.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            startActivityForResult(intent, 1001)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.imageView60.setOnClickListener { onBackPressed() }

        getAddress()
    }

    fun getAddress(){
        RetrofitMansae.server.getMyAddress()
                .enqueue(object : Callback<myAddress>{
                    override fun onFailure(call: Call<myAddress>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<myAddress>, response: Response<myAddress>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                for(i in response.body()!!.data){
                                    addressArrayList.add(
                                            MyAddress(
                                                    id = i.id,
                                                    name = i.name,
                                                    contact = i.contact,
                                                    email = i.email,
                                                    addressCode = i.addressCode,
                                                    address = i.address,
                                                    addressDetail = i.addressDetail,
                                                    defaultAddress = i.defaultAddress
                                            )
                                    )
                                }
                                val addressAdatper = DeliveryAdapter(addressArrayList)

                                binding.deliveryListRecyclerview.apply {
                                    adapter = addressAdatper
                                    layoutManager = LinearLayoutManager(this@DeliveryListActivity)
                                }

                                addressAdatper.setItemClickListner(object : DeliveryAdapter.ItemClickListener{
                                    override fun onClick(view: View, position: Int) {
                                        // 기본 배송지로 등록
                                        val intent = Intent()
                                        App.prefs.putBooleanData(Constant.DEFALUT_ADDRESS, true)
                                        App.prefs.putIntData(Constant.DEFALUT_ADDRESS_ID, addressArrayList[position].id)
                                        App.prefs.putStringData(Constant.ADDRESS_NAME, addressArrayList[position].name) // 이름
                                        App.prefs.putStringData(Constant.DELIVERY_PHONE, addressArrayList[position].contact) // 핸드폰번호
                                        App.prefs.putStringData(Constant.ADDRESS_CODE, addressArrayList[position].addressCode) // 주소 코드
                                        App.prefs.putStringData(Constant.ADDRESS, addressArrayList[position].address) // 주소
                                        App.prefs.putStringData(Constant.ADDRESS_DETAIL, addressArrayList[position].addressDetail) // 주소 상세
                                        App.prefs.putIntData(Constant.DEFAULT_POSITION, position)
                                        intent.putExtra("id", addressArrayList[position].id)
                                        setResult(Activity.RESULT_OK, intent)
                                        finish()
                                    }

                                    override fun onDeleteClick(view: View, position: Int) {
                                        deleteAddress(addressArrayList[position].id)
                                    }
                                })
                            }
                            else -> {
                                Toast.makeText(this@DeliveryListActivity, "서버오류", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun deleteAddress(id : Int){
        RetrofitMansae.server.deleteAddress(id = id)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                Toast.makeText(this@DeliveryListActivity, "배송지를 삭제했습니다.", Toast.LENGTH_SHORT).show()
                                addressArrayList.clear()
                                getAddress()
                            }
                            else -> {
                                Toast.makeText(this@DeliveryListActivity, "서버오류", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                1001 -> {

                    addressArrayList.clear()
                    getAddress()
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}