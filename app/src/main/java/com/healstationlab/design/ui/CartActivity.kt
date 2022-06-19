@file:Suppress("NAME_SHADOWING")

package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CartAdatper
import com.healstationlab.design.databinding.ActivityCartBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.cart
import com.healstationlab.design.model.Cart
import com.healstationlab.design.model.ShippingMent
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class CartActivity : AppCompatActivity(){

    lateinit var binding : ActivityCartBinding
    var cartArrayList : ArrayList<Cart> = arrayListOf()
//    val valcartArrayListCopy : ArrayList<Cart> = arrayListOf()
    var cartShipingList : ArrayList<ShippingMent> = arrayListOf()
    var totalship = 0
//    var first_total_money = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCartBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getCartList()

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        /** 주문하기 **/
        binding.orderButton.setOnClickListener {
            var check  = false
            val checkList: List<Cart> = cartArrayList.filter { it.isChecked == true }

            for(element in checkList){
                if(element.isChecked!!){
                    check = true
                    break
                }
            }

            if(check){
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("checkList", checkList as ArrayList<Cart>)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            } else {
                Toast.makeText(this, "상품을 체크해주세요!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    private fun getCartList(){
        RetrofitMansae.server.getCartList()
                .enqueue(object : Callback<cart>{
                    override fun onFailure(call: Call<cart>, t: Throwable) {

                    }

                    @SuppressLint("NotifyDataSetChanged")
                    override fun onResponse(call: Call<cart>, response: Response<cart>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                var total : Long = 0
                                for(i in response.body()!!.data){
                                    if(i.product.shippingPrice != 0){
                                        cartShipingList.add(
                                                ShippingMent(i.product.merchantName, i.product.shippingPrice)
                                        )
                                    }
                                    total += i.count * i.productOption.price.toLong()
                                    cartArrayList.add(
                                            Cart(
                                                    proId = i.product.id,
                                                    merchantName = i.product.merchantName,
                                                    price = i.productOption.price.toLong(),
                                                    count = i.count.toLong(),
                                                    name = i.product.name,
                                                    brand = i.product.brand,
                                                    shippingPrice = i.product.shippingPrice,
                                                    rating = if(i.product.cosSimilarity == null){
                                                        0.0
                                                    } else {
                                                        DecimalFormat("#.#").format(i.product.cosSimilarity.ratings!!).toDouble()
                                                    },
                                                    imageUrl = i.product.imageUrl,
                                                    id = i.productOption.id,
                                                    optionName = i.productOption.name
                                            )
                                    )
                                }
                                val cartAdapter = CartAdatper(cartArrayList)
                                cartAdapter.notifyDataSetChanged()

                                binding.cartRecyclerview.apply {
                                    adapter = cartAdapter
                                    layoutManager = LinearLayoutManager(this@CartActivity)
                                }

                                binding.textView234.text = sliceAmountNumber(total)


                                /** 카트 클릭 **/
                                cartAdapter.setItemClickListner(object : CartAdatper.ItemClickListener{
                                    override fun onClick(view: View, position: Int) {
                                        val intent = Intent(this@CartActivity, ProductDetailActivity::class.java)
                                        intent.putExtra("id", cartArrayList[position-1].proId)
                                        startActivity(intent)
                                        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                    }

                                    override fun onCloseClick(view: View, position: Int) {
                                        deleteCartItem(cartArrayList[position-1].id!!)
                                        if(cartArrayList[position-1].isChecked!!){
                                            binding.textView234.text = sliceAmountNumber(binding.textView234.text.toString().replace(",","").toInt().minus(cartArrayList[position-1].price!!.toInt() * cartArrayList[position-1].count!!.toInt()).toLong())
                                            cartArrayList.removeAt(position-1)
                                        }
                                        cartAdapter.notifyDataSetChanged()
                                        Toast.makeText(this@CartActivity, "삭제 완료", Toast.LENGTH_SHORT).show()
                                    }

                                    override fun onMinusClick(view: View, position: Int) {
                                        minusCartItem(cartArrayList[position-1].id!!)
                                        cartArrayList[position-1].count = cartArrayList[position-1].count!!.toInt().minus(1).toLong()
                                        if(cartArrayList[position-1].isChecked!!){
                                            binding.textView234.text = sliceAmountNumber(binding.textView234.text.toString().replace(",","").toInt().minus(cartArrayList[position-1].price!!.toInt()).toLong())
                                        }
                                        cartAdapter.notifyDataSetChanged()
                                    }

                                    override fun onPlusClick(view: View, position: Int) {
                                        plusCartItem(cartArrayList[position-1].id!!)
                                        cartArrayList[position-1].count = cartArrayList[position-1].count!!.toInt().plus(1).toLong()
                                        if(cartArrayList[position-1].isChecked!!){
                                            binding.textView234.text = sliceAmountNumber(binding.textView234.text.toString().replace(",","").toInt().plus(cartArrayList[position-1].price!!.toInt()).toLong())
                                        }
                                        cartAdapter.notifyDataSetChanged()
                                    }

                                    /** 상품 전체 선택 **/
                                    override fun allClick(boolean: Boolean, position: Int) {
                                        when(boolean){
                                            true -> {
                                                var total : Long = 0
                                                for(i in 0 until cartArrayList.size){
                                                    cartArrayList[i].isChecked = true
                                                    total += cartArrayList[i].price!! * cartArrayList[i].count!!
                                                }
                                                binding.textView234.text = sliceAmountNumber(total)
                                                cartAdapter.notifyDataSetChanged()
                                            }
                                            false -> {
                                                for(i in 0 until cartArrayList.size){
                                                    cartArrayList[i].isChecked = false
                                                }
                                                cartAdapter.notifyDataSetChanged()
                                                binding.textView234.text = "0"
                                            }
                                        }
                                    }

                                    @SuppressLint("NotifyDataSetChanged")
                                    override fun selectDelete(view: View, position: Int) {
                                        val clone : ArrayList<Cart> = arrayListOf()
                                        for(i in 0 until cartArrayList.size){
                                            if(cartArrayList[i].isChecked!!){
                                                deleteCartItem(cartArrayList[i].id!!)
                                                clone.add(cartArrayList[i])
                                            }
                                        }
                                        val size = clone.size
                                        for(i in 0 until size){
                                            binding.textView234.text = sliceAmountNumber(binding.textView234.text.toString().replace(",","").toInt().minus(clone[i].price!!.toInt() * clone[i].count!!.toInt()).toLong())
                                            cartArrayList.remove(clone[i])
                                        }
                                        cartAdapter.notifyDataSetChanged()
                                        Toast.makeText(this@CartActivity, "삭제 완료", Toast.LENGTH_SHORT).show()
                                    }

                                    @SuppressLint("NotifyDataSetChanged")
                                    override fun checkBox(view: View, position: Int) {
                                        when(cartArrayList[position].isChecked){
                                            true -> {
                                                binding.textView234.text = sliceAmountNumber(binding.textView234.text.toString().replace(",","").toInt().minus(cartArrayList[position].price!!.toInt() * cartArrayList[position].count!!.toInt()).toLong())
                                                cartArrayList[position].isChecked = false
                                                cartAdapter.notifyDataSetChanged()
                                            }
                                            false -> {
                                                // 가격 추가
                                                binding.textView234.text = sliceAmountNumber(binding.textView234.text.toString().replace(",","").toInt().plus(cartArrayList[position].price!!.toInt() * cartArrayList[position].count!!.toInt()).toLong())
                                                cartArrayList[position].isChecked = true
                                                cartAdapter.notifyDataSetChanged()
                                            }
                                            else -> {}
                                        }
                                    }
                                })



                                val list = cartShipingList.distinct()


                                for(element in list){
                                    totalship += element.shippingMent!!.toInt()
                                }

                            }

                            else -> {
                                Toast.makeText(this@CartActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun deleteCartItem(id: Int){
        RetrofitMansae.server.deleteCartItem(id = id)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {

                        when(response.body()?.responseCode){
                            "SUCCESS" -> {

                            }

                            else -> {
                                Toast.makeText(this@CartActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun plusCartItem(id: Int){
        RetrofitMansae.server.cartItemPlus(id = id)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {

                            }

                            else -> {
                                Toast.makeText(this@CartActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun minusCartItem(id: Int){
        RetrofitMansae.server.cartItemMinus(id = id)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {


                            }

                            else -> {
                                Toast.makeText(this@CartActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}