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
import com.healstationlab.design.dto.recommend
import com.healstationlab.design.model.Product
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class RecommendFragment : Fragment() {

    var scrollCheck = 0
    var page = 0
    val recommedList : ArrayList<Product> = arrayListOf() // ai추천
    var recommendRecyclerView : RecyclerView? = null
    var recommendAdatper : ProductAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recommend, container, false)
        recommendRecyclerView = view.findViewById(R.id.recommendRecyclerView)
        getRecommendAi(page)

        recommendRecyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()

                if(lastVisibleItemPosition == recommedList.size-1){
                    getRecommendAi(++page)
                }
            }
        })
        return view
    }

    fun getRecommendAi(page : Int){
        RetrofitMansae.server.getRecommendAi(pageNumber = page)
            .enqueue(object : Callback<recommend> {
                override fun onFailure(call: Call<recommend>, t: Throwable) {
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<recommend>, response: Response<recommend>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data) {
                                if(!i.hidden){
                                    if(i.options.isEmpty()){
                                        recommedList.add(
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
                                        recommedList.add(
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

                            /** 스크롤 올림 방지 **/
                            if(scrollCheck == 0){
                                scrollCheck = 1
                                recommendAdatper = ProductAdapter(recommedList, 0)
                                recommendRecyclerView.apply {
                                    this!!.adapter = recommendAdatper
                                    layoutManager = LinearLayoutManager(context)
                                }
                            }
                            recommendAdatper?.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, ProductDetailActivity::class.java)
                                    intent.putExtra("id", recommedList[position].id)
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
        recommedList.clear()
    }
}