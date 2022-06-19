@file:Suppress("SameParameterValue")

package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CartAdatper
import com.healstationlab.design.adapter.DetailOrderAdapter
import com.healstationlab.design.databinding.ActivityPaymentBinding
import com.healstationlab.design.dto.coupon
import com.healstationlab.design.dto.pointsDate
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.model.Cart
import com.healstationlab.design.model.Option
import com.healstationlab.design.model.ShippingMent
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.login.AgreementActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class PaymentActivity : AppCompatActivity() {

    lateinit var binding : ActivityPaymentBinding
    private var addTotal = 0
    private var merchantshppinglist : ArrayList<ShippingMent> = arrayListOf()
    private var gubun : String = "CARD"
    private var asnwer : String = ""
    var myPoint = 0
    var name = ""
    private var pointCheck = false
    private var couponCheck = false
    var shipmentDes = "" // 배송시 유의사항
    private var couponId = 0 // 쿠폰 id
    private var shipmentAddressId = 0 // 배송지 id
//    var userPoint = 0
    lateinit var test : ArrayList<Int>
    var cart : ArrayList<Cart> = arrayListOf()
    private lateinit var checkList: ArrayList<Cart>
    var size = 0
    var isShipBool = true

    var paymentDate = 0
    var purchase = 0.0
    var review = 0

    private var pointTotal = 0
    private var couponTotal = 0


    private var realTotalMoney = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView277.setOnClickListener {
            val intent = Intent(this, AgreementActivity ::class.java)
            intent.putExtra("term", "제3자")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.back.setOnClickListener {
            hideKeyboard()
        }

        binding.textView276.setOnClickListener {
            val intent = Intent(this, AgreementActivity ::class.java)
            intent.putExtra("term", "주문내역")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 저장된 배송지 정보 **/
        when(App.prefs.getBooleanData(Constant.DEFALUT_ADDRESS)){
            true -> {
                addressInfo(true)
                addressInfoMapping()
            }
            false -> {
                addressInfo(false)
            }
        }

        /** 결제수단 **/
        when(App.prefs.getStringData(Constant.PAYMENT_CHOOSE)){
            "CARD" -> {
                gubun = "CARD"
                paymentChoose(gubun)
            }
            "BANK" -> {
                gubun = "BANK"
                paymentChoose(gubun)
            }
            else -> { }
        }

        binding.textView267.setOnClickListener {
            gubun = "CARD"
            paymentChoose(gubun)
        }

        binding.textView268.setOnClickListener {
            gubun = "BANK"
            paymentChoose(gubun)
        }

        binding.checkBox3.setOnCheckedChangeListener { _, b ->
            when(b) {
                true -> {
                    if(gubun == "CARD"){
                        App.prefs.putStringData(Constant.PAYMENT_CHOOSE, "CARD")
                    } else if (gubun == "BANK"){
                        App.prefs.putStringData(Constant.PAYMENT_CHOOSE, "BANK")
                    }
                }
                else -> {}
            }
        }

        /** 배송지 추가 **/
        binding.addAddress.setOnClickListener {
            val intent = Intent(this, AddAddressActivity::class.java)
            startActivity(intent)
        }

        /** 배송지 목록**/
        binding.deliveryListButton.setOnClickListener {
            val intent = Intent(this, DeliveryListActivity::class.java)
            startActivityForResult(intent, 1002)
        }

        /** 배송 요청 사항 **/
        val spinnerItems = arrayListOf("요청 사항 선택", "문 앞에 놓아주세요", "경비실에 맡겨주세요", "배송 전 전화해주세요", "직접 입력")
        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.spinner2.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    if(position == 4){
                        isShipBool = false
                        binding.editTextTextPersonName15.isVisible = true
                        shipmentDes = ""

                    } else {
                        isShipBool = true
                        binding.editTextTextPersonName15.isVisible = false
                        shipmentDes = spinnerItems[position]
                    }

                }
            }
        }

        /** 뒤로가기 **/
        binding.appCompatButton8.setOnClickListener { onBackPressed() }

        /** 전체 동의 **/
        binding.checkBox4.setOnCheckedChangeListener { _, _ ->
            when(binding.checkBox4.isChecked){
                true -> {
                    binding.checkBox5.isChecked = true
                    binding.checkBox6.isChecked = true
                }
                false -> {
                    binding.checkBox5.isChecked = false
                    binding.checkBox6.isChecked = false
                }
            }
        }

        /** 결제하기 **/
        binding.textView278.setOnClickListener {
            if(binding.checkBox5.isChecked && binding.checkBox6.isChecked){
                if(shipmentDes != "요청 사항 선택") {
                    if (shipmentAddressId != 0) {
                        when (asnwer) {
                            "cart" -> {
                                val intent = Intent(this, PGActivity::class.java)
                                val goods: String = when (checkList.size == 1) {
                                    true -> {
                                        checkList[0].name.toString()
                                    }
                                    false -> {
                                        checkList[0].name.toString() + "외 ${checkList.size - 1}건"
                                    }
                                }
                                intent.putExtra("goodsName", goods) // 상품이름
                                intent.putExtra(
                                    "totalPrice",
                                    binding.totalMoney.text.toString().replace(",", "")
                                ) // 상품가격
                                intent.putExtra("payment", gubun) // 지불수단
                                intent.putExtra(
                                    "o_id",
                                    (System.currentTimeMillis() / 1000).toInt()
                                        .toString() + App.prefs.getIntData(Constant.UID)
                                        .toString() + rand(0, 10).toString()
                                )
                                intent.putExtra("pay", checkList)
                                intent.putExtra("deliveryCount", merchantshppinglist)
                                intent.putExtra("shipmentAddressId", shipmentAddressId)
                                intent.putExtra("couponId", couponId)
                                if (isShipBool) {
                                    intent.putExtra("deliveryInstruction", shipmentDes)
                                } else {
                                    intent.putExtra(
                                        "deliveryInstruction",
                                        binding.editTextTextPersonName15.text.toString()
                                    )
                                }
                                intent.putExtra("paymentDate", paymentDate)
                                intent.putExtra("purchase", purchase)
                                intent.putExtra("review", review)

                                if (binding.editTextTextPersonName12.text.toString() != "") {
                                    intent.putExtra("point", binding.editTextTextPersonName12.text.toString().toInt())
                                } else {
                                    intent.putExtra("point", 0)
                                }
                                intent.putExtra("gubun", "cart")
                                startActivity(intent)
                            }

                            "buy" -> {
                                val intent = Intent(this, PGActivity::class.java)
                                intent.putExtra("goodsName", cart[0].name.toString()) // 상품이름
                                intent.putExtra("totalPrice", binding.totalMoney.text.toString().replace(",", "")) // 상품가격
                                intent.putExtra("payment", gubun) // 지불수단
                                intent.putExtra(
                                    "o_id",
                                    (System.currentTimeMillis() / 1000).toInt()
                                        .toString() + App.prefs.getIntData(Constant.UID)
                                        .toString() + rand(0, 10).toString()
                                )
                                intent.putExtra("pay", cart)
                                intent.putExtra("deliveryCount", merchantshppinglist)
                                intent.putExtra("shipmentAddressId", shipmentAddressId)
                                intent.putExtra("couponId", couponId)
                                if (isShipBool) {
                                    intent.putExtra("deliveryInstruction", shipmentDes)
                                } else {
                                    intent.putExtra(
                                        "deliveryInstruction",
                                        binding.editTextTextPersonName15.text.toString()
                                    )
                                }
                                intent.putExtra("deliveryInstruction", shipmentDes)
                                intent.putExtra("paymentDate", paymentDate)
                                intent.putExtra("purchase", purchase)
                                intent.putExtra("review", review)


                                intent.putExtra("gubun", "buy")
                                startActivity(intent)
                            }
                        }
                    } else {
                        Toast.makeText(this, "배송지를 설정해주세요!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "배송지 요청사항을 설정해주세요", Toast.LENGTH_SHORT).show()
                }

            } else {
                Toast.makeText(this, "필수 동의를 클릭해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        /** 보유 쿠폰 클릭 **/
        binding.couponSizeText.setOnClickListener {
            if(!couponCheck){
                val intent = Intent(this, DcCouponActivity::class.java)
                intent.putExtra("price", binding.totalMoney.text.toString().replace(",", "").toLong())
                startActivityForResult(intent, 1004)
            }
        }

        /** 포인트 최대 사용 **/
        binding.textView251.setOnClickListener {
            if(binding.totalMoney.text.toString().replace(",","").toInt() >= 10000){ // 1만원 이상
                if(!pointCheck){
                    binding.editTextTextPersonName12.setText(myPoint.toString())
                    pointCheck = true
                    binding.textView251.isEnabled = false
//                    binding.textView248.text = sliceAmountNumber(myPoint.toString().toInt().plus(pointTotal).plus(couponTotal.toLong()))+"원"
                }
            } else {
                Toast.makeText(this, "포인트는 10,000원 이상 사용 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.editTextTextPersonName12.addTextChangedListener {
            if( binding.editTextTextPersonName12.text.toString() == ""){
                pointTotal = 0
                realTotalMoney = realTotalMoney.replace(",","")
                realTotalMoney = realTotalMoney.toInt().plus(pointTotal).toString()
                binding.textView248.text = sliceAmountNumber(pointTotal.toLong().plus(couponTotal))+"원"

                binding.totalMoney.text = sliceAmountNumber(realTotalMoney.toLong())
            }else if (binding.editTextTextPersonName12.text.toString().toInt() > myPoint) {
                Toast.makeText(this, "보유한 포인트보다 높습니다.", Toast.LENGTH_SHORT).show()
                binding.editTextTextPersonName12.setText("")
            } else {
                pointTotal = binding.editTextTextPersonName12.text.toString().replace(",","").toInt()
                binding.totalMoney.text = (sliceAmountNumber(realTotalMoney.replace(",","").toLong().minus(pointTotal)))
                binding.textView248.text = sliceAmountNumber(pointTotal.toLong().plus(couponTotal))+"원"
            }
        }

        getCoupon()

        if(intent.getSerializableExtra("list") == null){
            getCheckList()
            asnwer = "cart"
        } else {
            asnwer = "buy"
            getBuyItem()
        }
        getUser()
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                /** 배송지 선택 후 **/
                1002 -> {
                    shipmentAddressId = data!!.getIntExtra("id", 0)
                    addressInfo(true)
                    addressInfoMapping()
                }

                1004 -> {
                    couponCheck = true
                    couponId =  data!!.getIntExtra("id", 0)
                    binding.textView246.text = sliceAmountNumber(data.getIntExtra("sale", 0).toLong()) +"원"
                    couponTotal = data.getIntExtra("sale", 0)
                    binding.textView248.text = sliceAmountNumber(data.getIntExtra("sale", 0).toLong().plus(binding.textView248.text.toString().replace(",","").replace("원","").toInt()))+"원"
                    binding.totalMoney.text =
                        sliceAmountNumber(binding.totalMoney.text.toString().replace(",", "").toInt().minus(
                            data.getIntExtra("sale", 0)).toLong())
                    // addTotal = sliceAmountNumber(binding.totalMoney.text.toString().toLong()).toInt()
                }

                else -> {

                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun addressInfoMapping(){
        shipmentAddressId = App.prefs.getIntData(Constant.DEFALUT_ADDRESS_ID)
        binding.name.text = App.prefs.getStringData(Constant.ADDRESS_NAME)
        binding.phone.text = App.prefs.getStringData(Constant.DELIVERY_PHONE)
        binding.addressCode.text = "(${App.prefs.getStringData(Constant.ADDRESS_CODE)}) ${App.prefs.getStringData(Constant.ADDRESS)} ${App.prefs.getStringData(Constant.ADDRESS_DETAIL)}"
    }

    private fun addressInfo(bool : Boolean){
        binding.name.isVisible = bool
        binding.defalult.isVisible = bool
        binding.addressCode.isVisible = bool
        binding.phone.isVisible = bool
        binding.addAddress.isVisible = !bool
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun getCheckList() {
        var total: Long = 0

        checkList = intent.getSerializableExtra("checkList") as ArrayList<Cart>

        for (i in 0 until checkList.size) {
            total += checkList[i].count!! * checkList[i].price!!.toLong() // 총 비용
            merchantshppinglist.add(ShippingMent(checkList[i].merchantName, checkList[i].shippingPrice)) // 상품 입점사, 배송비 정보
        }
        size = checkList.size
        getPoints()

        val lastlist = merchantshppinglist

        val hash : Hashtable<String, Int> = Hashtable()

        for(i in 0 until lastlist.size) {
            if(!hash.containsKey(lastlist[i].merchant)){
                hash["${lastlist[i].merchant}"] = lastlist[i].shippingMent
            } else {
                if( hash["${lastlist[i].merchant}"]!! < lastlist[i].shippingMent!!){
                    hash["${lastlist[i].merchant}"] = lastlist[i].shippingMent
                }
            }
        }



        val list1 = ArrayList(hash.keys)
        val list2 = ArrayList(hash.values)


        for(i in 0 until merchantshppinglist.size){
            for(j in 0 until list1.size){
                if(merchantshppinglist[i].merchant == list1[j] && merchantshppinglist[i].shippingMent == list2[j]){
                    merchantshppinglist[i].isBool = true
                }
            }
        }

        val list = hash.values
        test = list.filter { it > 0 } as ArrayList<Int>

        var totalShip = 0
        for (i in 0 until test.size){
            totalShip += test[i]
        }


        binding.totalAmount.text = sliceAmountNumber(total)
        binding.totalShipment.text = sliceAmountNumber(totalShip.toLong())
        binding.totalMoney.text = sliceAmountNumber(total + totalShip)
        realTotalMoney = sliceAmountNumber(total + totalShip)
        addTotal = sliceAmountNumber(total + totalShip).replace(",","").toInt()

        val cartAdapter = CartAdatper(checkList)
        cartAdapter.notifyDataSetChanged()

        val detailOrderAdapter = DetailOrderAdapter(this@PaymentActivity, checkList)

        /** 카트 리스트 **/
        binding.orderProductRecyclerview.apply {
            adapter = detailOrderAdapter
            layoutManager = LinearLayoutManager(this@PaymentActivity)
        }

        detailOrderAdapter.setItemClickListner(object : DetailOrderAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

            }
        })
    }


    private fun paymentChoose(gubun : String){
        when(gubun){
            "CARD" -> {
                binding.textView267.setTextColor(Color.parseColor("#5A2D8B"))
                binding.textView267.setBackgroundResource(R.drawable.stroke_green)
                binding.textView268.setTextColor(Color.parseColor("#777777"))
                binding.textView268.setBackgroundResource(R.drawable.stroke_gray)
            }

            "BANK" -> {
                binding.textView267.setTextColor(Color.parseColor("#777777"))
                binding.textView267.setBackgroundResource(R.drawable.stroke_gray)
                binding.textView268.setTextColor(Color.parseColor("5A2D8B"))
                binding.textView268.setBackgroundResource(R.drawable.stroke_green)
            }
        }
    }

    private fun getCoupon(){
        RetrofitMansae.server.getCoupon()
                .enqueue(object : Callback<coupon>{
                    override fun onFailure(call: Call<coupon>, t: Throwable) {

                    }

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<coupon>, response: Response<coupon>) {

                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                binding.couponSizeText.text = "${response.body()!!.data.size}개 보유"
                            }
                            else -> {
                                Toast.makeText(this@PaymentActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    private fun getBuyItem(){
        var totalAmount = 0
        var totalShip = 0

        val option = intent.getSerializableExtra("list") as ArrayList<Option>

        for(i in 0 until option.size){
            cart.add(
                    Cart(
                        id = option[i].id,
                        optionName = option[i].name,
                        merchant = intent.getStringExtra("merchant"),
                        brand = option[i].brand, name = intent.getStringExtra("name"),
                        price = option[i].price, imageUrl = option[i].img,
                        shippingPrice = option[i].shipment,
                        merchantName = option[i].merchant,
                        count = option[i].cnt.toLong()
                    )
            )
            size = cart.size
            getPoints()
            totalAmount += (option[i].price.toInt() * option[i].cnt)
            totalShip = option[i].shipment
        }
        val total = totalAmount + totalShip

        realTotalMoney = sliceAmountNumber(total.toLong())

        binding.totalAmount.text = sliceAmountNumber(totalAmount.toLong())
        binding.totalShipment.text = sliceAmountNumber(totalShip.toLong())
        binding.totalMoney.text = sliceAmountNumber(total.toLong())

        val detailOrderAdapter = DetailOrderAdapter(this, cart)

        binding.orderProductRecyclerview.apply {
            adapter = detailOrderAdapter
            layoutManager = LinearLayoutManager(this@PaymentActivity)
        }

        detailOrderAdapter.setItemClickListner(object : DetailOrderAdapter.ItemClickListener{
            override fun onClick(view: View, position: Int) {

            }
        })
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }

    private fun rand(from: Int, to: Int) : Int {
        val random = Random()
        return random.nextInt(to - from) + from
    }

    private fun getUser(){
        RetrofitMansae.server.getUserInfo()
            .enqueue(object : Callback<userInfo>{
                override fun onFailure(call: Call<userInfo>, t: Throwable) {
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            myPoint = response.body()!!.data.points
                            binding.textView254.text = myPoint.toString()+"P"
                            binding.textView255.text = myPoint.toString()+"P"
                            binding.textView240.text = response.body()!!.data.name.toString()
                            binding.textView241.text = response.body()!!.data.contact.toString()
                            binding.textView259.text = response.body()!!.data.username
                        }
                    }
                }
            })
    }

    private fun getPoints(){
        RetrofitMansae.server.getPoints()
                .enqueue(object : Callback<pointsDate>{
                    override fun onFailure(call: Call<pointsDate>, t: Throwable) {

                    }

                    @SuppressLint("SetTextI18n")
                    override fun onResponse(call: Call<pointsDate>, response: Response<pointsDate>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                review = response.body()!!.data!!.review!! * size // 리뷰
                                binding.textView274.text = review.toString()+"P"
                                val percent = response.body()!!.data!!.purchase!! / 100
                                binding.textView273.text = percent.times(binding.totalAmount.text.toString().replace(",","").toLong()).toInt().toString()+"P"

                                paymentDate = response.body()!!.data!!.paymentDate!!
                                purchase = response.body()!!.data!!.purchase!! // 기본적립

                            }
                        }
                    }
                })
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTextPersonName15.windowToken, 0)
        imm.hideSoftInputFromWindow(binding.editTextTextPersonName12.windowToken, 0)
    }
}