package com.healstationlab.design.fragment_nesting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.adapter.ProductAdapter
import com.healstationlab.design.dto.deal
import com.healstationlab.design.model.Product
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class SaleFragment : Fragment() {

    val saleList : ArrayList<Product> = arrayListOf() // 랭크
    var saleRecyclerView : RecyclerView? = null
    var scrollCheck = 0
    var page = 0
    var recommendAdatper : ProductAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sale, container, false)
        saleRecyclerView = view.findViewById(R.id.saleRecyclerView)
        getSale(page)

        saleRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                if(lastVisibleItemPosition == saleList.size-1){
                    getSale(++page)
                }
            }
        })
        return view
    }

    fun getSale(page : Int){
        RetrofitMansae.server.getDeal(pageSize = 50, pageNumber = page,recommendationProductCode = App.prefs.getStringData(Constant.CODE).toString())
            .enqueue(object : Callback<deal> {
                override fun onFailure(call: Call<deal>, t: Throwable) {

                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<deal>, response: Response<deal>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data) {
                                if(!i.hidden){
                                    if(i.options.isEmpty()){
                                        saleList.add(
                                                Product(
                                                        i.id,
                                                        i.imageUrl,
                                                        if(i.cosSimilarity == null){
                                                            0.0
                                                        } else {
                                                            DecimalFormat("#.#").format(i.cosSimilarity.ratings!!).toDouble()
                                                        },
                                                        i.brand,
                                                        i.name,
                                                        ""
                                                )
                                        )
                                    } else {
                                        saleList.add(
                                                Product(
                                                        i.id,
                                                        i.imageUrl,
                                                        if(i.cosSimilarity == null){
                                                            0.0
                                                        } else {
                                                            DecimalFormat("#.#").format(i.cosSimilarity.ratings!!).toDouble()
                                                        },
                                                        i.brand,
                                                        i.name,
                                                        i.options[0].price.toString()
                                                )
                                        )
                                    }
                                }
                            }

                            if(scrollCheck == 0){
                                scrollCheck = 1
                                recommendAdatper = ProductAdapter(saleList, 0)
                                saleRecyclerView.apply {
                                    this!!.adapter = recommendAdatper
                                    layoutManager = LinearLayoutManager(context)
                                }
                            }

                            recommendAdatper?.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, ProductDetailActivity::class.java)
                                    intent.putExtra("id", saleList[position].id)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                }
                            })
                            recommendAdatper?.notifyDataSetChanged()
                        }

                        else -> {
                            // Toast.makeText()
                        }
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        page = 0
        scrollCheck = 0
        saleList.clear()
    }
}