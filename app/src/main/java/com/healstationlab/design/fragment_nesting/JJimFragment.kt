package com.healstationlab.design.fragment_nesting

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.adapter.FavoriteAdapter
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.favoriteDTO
import com.healstationlab.design.model.Cart
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.ProductDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class JJimFragment : Fragment() {

    lateinit var textView369 : TextView
    private lateinit var textView371 : TextView
    lateinit var recyclerview : RecyclerView

    var favoriteList : ArrayList<Cart> = arrayListOf()
    var favoriteAdapter : FavoriteAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_j_jim, container, false)


        textView369 = view!!.findViewById(R.id.textView369)
        textView371 = view.findViewById(R.id.textView371)
        recyclerview = view.findViewById(R.id.recyclerview)

        textView371.setOnClickListener {
            for(i in 0 until favoriteList.size){
                postFavorites(favoriteList[i].id!!, "all")
            }
            Toast.makeText(context!!, "전체삭제완료!", Toast.LENGTH_SHORT).show()
            favoriteList.clear()
            getFavoriteList()
        }

        getFavoriteList()

        return view
    }

    fun getFavoriteList(){

        RetrofitMansae.server.getFavorites()
                .enqueue(object : Callback<favoriteDTO>{
                    override fun onFailure(call: Call<favoriteDTO>, t: Throwable) {

                    }

                    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
                    override fun onResponse(call: Call<favoriteDTO>, response: Response<favoriteDTO>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                if(response.body()?.data != null){
                                    textView369.text = "전체 ${response.body()!!.data.size}개"

                                    for(i in response.body()!!.data){
                                        favoriteList.add(
                                                Cart(
                                                        merchantName = i.merchant?.name,
                                                        price = i.options[0].price.toLong(),
                                                        name = i.name,
                                                        brand = i.brand,
                                                        rating = if(i.cosSimilarity == null){
                                                            0.0
                                                        } else {
                                                            DecimalFormat("#.#").format(i.cosSimilarity.ratings!!).toDouble()
                                                        },
                                                        imageUrl = i.imageUrl,
                                                        id = i.id
                                                )
                                        )
                                    }
                                    favoriteAdapter = FavoriteAdapter(favoriteList)
                                    favoriteAdapter!!.notifyDataSetChanged()

                                    favoriteAdapter!!.setItemClickListner(object : FavoriteAdapter.ItemClickListener{
                                        override fun onClick(view: View, position: Int) {
                                            val intent = Intent(context!!, ProductDetailActivity::class.java)
                                            intent.putExtra("id", favoriteList[position].id)
                                            startActivity(intent)
                                            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                        }

                                        override fun onClose(view: View, position: Int) {
                                            postFavorites(favoriteList[position].id!!)
                                        }
                                    })

                                    recyclerview.apply {
                                        adapter = favoriteAdapter
                                        layoutManager = LinearLayoutManager(context)
                                    }
                                }
                            }
                            else -> {

                            }
                        }
                    }
                })
    }

    fun postFavorites(id : Int, gubun : String = ""){
        RetrofitMansae.server.favorites(id = id)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                when(gubun){
                                    "all" -> {

                                    } else -> {
                                        favoriteList.clear()
                                        getFavoriteList()
                                        Toast.makeText(context!!, "삭제완료!", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }
                    }
                })
    }


    override fun onDetach() {
        super.onDetach()
        favoriteList.clear()
    }
}