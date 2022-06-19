package com.healstationlab.design.fragment_nesting

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.adapter.OrderAdapter
import com.healstationlab.design.dto.myOrderDTO
import com.healstationlab.design.model.myOrderListModel
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.OrderActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderFragment : Fragment() {

    var orderList : ArrayList<myOrderListModel> = arrayListOf()
    lateinit var orderRecuclerView : RecyclerView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_order, container, false)
        orderRecuclerView = view.findViewById(R.id.orderRecyclerView)
        getMyOrder()
        return view
    }

    private fun getMyOrder(){
        RetrofitMansae.server.getMyOrder()
            .enqueue(object : Callback<myOrderDTO> {
                override fun onFailure(call: Call<myOrderDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<myOrderDTO>, response: Response<myOrderDTO>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            orderList.clear()
                            for(i in response.body()!!.data[1].ProductOrderMeta){
                                if(i.orderItems.size != 1){ // 다수인경우
                                    orderList.add(
                                        myOrderListModel(
                                            orderNo = i.orderItems[0].orderNo,
                                            create = i.createdAt,
                                            imgUrl = i.orderItems[0].product.imageUrl,
                                            orderId = i.id,
                                            name = "${i.orderItems[0].product.name}외 ${i.orderItems.size-1}건",
                                            price = i.totalPrice.toLong(),
                                            amount = i.amount
                                        )
                                    )
                                } else { // 다수가 아닌경우
                                    orderList.add(myOrderListModel(
                                        orderNo = i.orderItems[0].orderNo,
                                        create = i.createdAt,
                                        imgUrl = i.orderItems[0].product.imageUrl,
                                        orderId = i.id,
                                        name = i.orderItems[0].product.name,
                                        price = i.totalPrice.toLong(),
                                        amount = i.amount
                                    ))
                                }
                            }

                            val orderAdapter = OrderAdapter(orderList)
                            orderRecuclerView.apply {
                                adapter = orderAdapter
                                layoutManager = LinearLayoutManager(context)
                            }

                            orderAdapter.setItemClickListner(object : OrderAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, OrderActivity::class.java)
                                    intent.putExtra("id", orderList[position].orderId)
                                    intent.putExtra("orderNo", orderList[position].orderNo)
                                    intent.putExtra("position", position)
                                    startActivityForResult(intent, 1010)
                                    activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                }
                            })
                        }
                        else -> {

                        }
                    }
                }
            })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                1010 -> {
                    getMyOrder()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        orderList.clear()
    }
}