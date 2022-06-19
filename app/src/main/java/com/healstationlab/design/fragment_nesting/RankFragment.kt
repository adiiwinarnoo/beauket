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
import com.healstationlab.design.dto.rank
import com.healstationlab.design.model.Product
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class RankFragment : Fragment() {

    val rankList : ArrayList<Product> = arrayListOf() // 랭크
    var rankRecyclerView : RecyclerView? = null
    var scrollCheck = 0
    var page = 0
    var recommendAdatper : ProductAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_rank, container, false)
        rankRecyclerView = view.findViewById(R.id.rankRecyclerView)
        getRank(page)

        rankRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                if(lastVisibleItemPosition == rankList.size-1){

                    getRank(++page)
                }
            }
        })

        return view
    }

    fun getRank(page : Int){
        RetrofitMansae.server.getRank(pageNumber = page)
            .enqueue(object : Callback<rank> {
                override fun onFailure(call: Call<rank>, t: Throwable) {

                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<rank>, response: Response<rank>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data) {
                                if(!i.hidden){
                                    if(i.options.isEmpty()){
                                        rankList.add(
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
                                        rankList.add(
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
                                recommendAdatper = ProductAdapter(rankList, 0)
                                rankRecyclerView.apply {
                                    this!!.adapter = recommendAdatper
                                    layoutManager = LinearLayoutManager(context)
                                }
                            }

                            recommendAdatper?.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, ProductDetailActivity::class.java)
                                    intent.putExtra("id", rankList[position].id)
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
        rankList.clear()
    }
}