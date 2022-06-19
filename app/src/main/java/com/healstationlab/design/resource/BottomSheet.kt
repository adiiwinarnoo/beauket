package com.healstationlab.design.resource

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.healstationlab.design.R
import com.healstationlab.design.adapter.OptionAdatper
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.option
import com.healstationlab.design.model.Option
import com.healstationlab.design.ui.CartActivity
import com.healstationlab.design.ui.PaymentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class BottomSheet : BottomSheetDialogFragment() {

    val optionArrayList : ArrayList<Option> = arrayListOf()
    val optionNameList : ArrayList<String> = arrayListOf()
    val optionPriceList : ArrayList<Long> = arrayListOf()
    private var idx = 0
    var brand = ""
    var url = ""
    var optionAdapter : OptionAdatper? = null
//    val cntArrayList : ArrayList<Int> = arrayListOf()

    var switch : Boolean = true
    var spinner : Spinner? = null

    var optionRecyclerView : RecyclerView? = null
    var total : TextView? = null
    var amount : TextView? = null
    var next = true

    var merchant = ""
    var shipPrice = 0
    var name = ""

    override fun getTheme(): Int {
        return R.style.CustomBottomSheetDialog
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.bottom_sheet, container, false)
        val args = arguments
        name = args!!.getString("name").toString()
        idx = args.getInt("idx")
        brand = args.getString("brand").toString()
        url = args.getString("url").toString()
        merchant = args.getString("merchant").toString()
        shipPrice = args.getInt("shipPrice")



        total = view.findViewById(R.id.total)
        spinner = view.findViewById(R.id.spinner5)
        amount = view.findViewById(R.id.amount)
        optionRecyclerView = view.findViewById(R.id.optionRecyclerView)

        val textView285 = view.findViewById<TextView>(R.id.textView285) // 상품명
        val cartbutton = view.findViewById<TextView>(R.id.cart_button)

        textView285.text = name

        /** 장바구니 **/
        cartbutton.setOnClickListener {
            var stocks = true
            if (optionArrayList.size == 0) {
                Toast.makeText(context, "옵션을 추가해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                for (i in 0 until optionArrayList.size) {
                    if (optionArrayList[i].stocks < optionArrayList[i].cnt) {
                        stocks = false
                        break
                    } else {
                        val body: HashMap<String, String> = HashMap()
                        body["count"] = optionArrayList[i].cnt.toString()
                        addCart(id = optionArrayList[i].id, body = body)
                    }
                }
                if(stocks){
                    val intent = Intent(context, CartActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "임시 품절 상품입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        val buybutton = view.findViewById<TextView>(R.id.buy_button)

        /** 구매하기 **/
        buybutton.setOnClickListener {
            var stocks = true
            if (optionArrayList.size == 0) {
                Toast.makeText(context, "옵션을 추가해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                for (i in 0 until optionArrayList.size) {
                    if (optionArrayList[i].stocks < optionArrayList[i].cnt) {
                        stocks = false
                        break
                    }
                }
                if (stocks) {

                    val intent = Intent(context, PaymentActivity::class.java)
                    intent.putExtra("list", optionArrayList)
                    intent.putExtra("name", name)
                    intent.putExtra("brand", brand)
                    intent.putExtra("merchant", merchant)
                    startActivity(intent)
                } else {
                    Toast.makeText(context, "임시 품절 상품입니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }

        getOption()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view?.findViewById<ImageView>(R.id.back_button)?.setOnClickListener {
            dismiss()
        }
    }

    private fun getOption(){

        RetrofitMansae.server.getOption(id = idx)
                .enqueue(object : Callback<option>{
                    override fun onFailure(call: Call<option>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<option>, response: Response<option>) {
                        for(i in response.body()!!.data){
                            optionNameList.add(i.name)
                            optionPriceList.add(i.price)
                        }

                        if(response.body()!!.data.size != 1){
                            optionNameList.add(0, "옵션선택")
                        }

                        val spinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, optionNameList)

                        spinner.apply {
                            this!!.adapter = spinnerAdapter
                            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                                override fun onNothingSelected(p0: AdapterView<*>?) {

                                }

                                @SuppressLint("NotifyDataSetChanged")
                                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                                    /** 옵션이 하나인 경우 **/
                                    if(optionNameList.size == 1){
                                        optionArrayList.add(
                                                Option(
                                                        response.body()!!.data[position].name,
                                                        response.body()!!.data[position].price,
                                                        response.body()!!.data[position].originalPrice,
                                                        response.body()!!.data[position].stocks,
                                                        response.body()!!.data[position].id,
                                                        merchant = merchant,
                                                        shipment = shipPrice,
                                                        brand = brand,
                                                        img = url,
                                                        optionName = response.body()!!.data[position].name
                                                )
                                        )
                                        amount!!.text = sliceAmountNumber(response.body()!!.data[position].price.toString().toInt().plus(amount!!.text.toString().replace(",", "").toInt()).toString())
                                    } else {
                                        // amount!!.text = sliceAmountNumber(response.body()!!.data[position].price.toString().toInt().plus(amount!!.text.toString().replace(",", "").toInt()).toString())
                                        if(switch){
                                            switch = false
                                        } else {
                                            optionArrayList.add(
                                                    Option(
                                                            response.body()!!.data[position-1].name,
                                                            response.body()!!.data[position-1].price,
                                                            response.body()!!.data[position-1].originalPrice,
                                                            response.body()!!.data[position-1].stocks,
                                                            response.body()!!.data[position-1].id,
                                                            merchant = merchant,
                                                            shipment = shipPrice,
                                                            brand = brand,
                                                            img = url,
                                                            optionName = response.body()!!.data[position-1].name
                                                    )
                                            )
                                            amount!!.text = sliceAmountNumber(response.body()!!.data[position-1].price.toString().toInt().plus(amount!!.text.toString().replace(",", "").toInt()).toString())
                                        }
                                    }


                                    optionAdapter = OptionAdatper(optionArrayList)
                                    total!!.text = optionArrayList.size.toString()
                                    optionAdapter!!.notifyDataSetChanged()

                                    optionRecyclerView.apply {
                                        this!!.adapter = optionAdapter
                                        layoutManager = LinearLayoutManager(context)
                                    }

                                    optionAdapter!!.notifyDataSetChanged()

                                    optionAdapter!!.setItemClickListner(object : OptionAdatper.ItemClickListener {
                                        override fun onClick(view: View, position: Int) {

                                        }

                                        override fun plusClick(view: View, position: Int) {
                                            optionAdapter!!.notifyDataSetChanged()
                                            optionArrayList[position].cnt += 1
                                            total!!.text = total!!.text.toString().toInt().plus(1).toString()
                                            amount!!.text = sliceAmountNumber(amount!!.text.toString().replace(",", "").toInt().plus(optionArrayList[position].price).toString())
                                        }

                                        override fun minusClick(view: View, position: Int) {
                                            optionAdapter!!.notifyDataSetChanged()
                                            if (total!!.text.toString() != "1"){
                                                optionArrayList[position].cnt -= 1
                                                total!!.text = total!!.text.toString().toInt().minus(1).toString()
                                                amount!!.text = amount!!.text.toString().replace(",", "").toInt().minus(optionArrayList[position].price).toString()
                                            }
                                        }

                                        override fun delete(view: View, position: Int) {
                                            total!!.text = total!!.text.toString().toInt().minus(1).toString()
                                            amount!!.text = amount!!.text.toString().replace(",", "").toInt().minus((optionArrayList[position].cnt * optionArrayList[position].price).toInt()).toString()
                                            optionArrayList.removeAt(position)
                                            optionAdapter!!.notifyDataSetChanged()
                                        }
                                    })
                                }
                            }
                        }
                    }
                })
    }

    private fun addCart(id : Int, body : HashMap<String, String>){
        RetrofitMansae.server.postAddCard(
            id =  id,
            body = body
        ).enqueue(object : Callback<auth>{
            override fun onFailure(call: Call<auth>, t: Throwable) {

            }

            override fun onResponse(call: Call<auth>, response: Response<auth>) {

                when(response.body()!!.responseCode){
                    "SUCCESS" -> {

                    }

                    else -> {

                    }
                }
            }
        })
    }

    fun sliceAmountNumber(number : String) : String {
        val number2 = number.toLong()
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number2)
    }
}