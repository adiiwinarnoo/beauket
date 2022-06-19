package com.healstationlab.design.fragment_nesting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.adapter.InqueryAdapter
import com.healstationlab.design.dto.defaultInqueryDTO
import com.healstationlab.design.model.Inquery
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DefaultInqueryFragment : Fragment() {

    lateinit var recyuclerview : RecyclerView
    var inqueryAdapter : InqueryAdapter? = null
    var inqueryArrayList : ArrayList<Inquery> = arrayListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_default_inquery, container, false)
        recyuclerview = view.findViewById(R.id.recyuclerview)
        getInquery()
        return view
    }

    private fun getInquery(){
        RetrofitMansae.server.defaultInquery()
                .enqueue(object : Callback<defaultInqueryDTO>{
                    override fun onFailure(call: Call<defaultInqueryDTO>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<defaultInqueryDTO>, response: Response<defaultInqueryDTO>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                for(i in response.body()!!.data){
                                    inqueryArrayList.add(
                                            Inquery(content = i.content, answered = i.answered, id = i.id, createdAt = i.createdAt, type = i.type)
                                    )
                                }
                                inqueryAdapter = InqueryAdapter(inqueryArrayList)

                                inqueryAdapter!!.setItemClickListner(object : InqueryAdapter.ItemClickListener{
                                    override fun onClick(view: TextView, view2 : TextView, position: Int) {
//                                        getInqueryResponse(inqueryArrayList[position].id, view, view2)
                                    }
                                })

                                recyuclerview.apply {
                                    adapter = inqueryAdapter
                                    layoutManager = LinearLayoutManager(context)
                                }
                            }
                        }
                    }
                })
    }

//    fun getInqueryResponse(id : Int, text : TextView, create : TextView){
//        Retrofit_Mansae.server.getInqueryResponse(id = id)
//                .enqueue(object : Callback<inqueryResponse>{
//                    override fun onFailure(call: Call<inqueryResponse>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(call: Call<inqueryResponse>, response: Response<inqueryResponse>) {
//                        when(response.body()?.responseCode){
//                            "SUCCESS" -> {
//
//                                text.text = response.body()!!.data.content
//                                create.text = response.body()!!.data.createdDate
////                                inqueryArrayList[position].reply = response.body()!!.data.content
////                                inqueryAdapter!!.notifyItemChanged(position, "click")
//                            }
//                        }
//                    }
//                })
//    }

    override fun onDestroy() {
        super.onDestroy()
        inqueryArrayList.clear()
    }

    override fun onDetach() {
        super.onDetach()
        inqueryArrayList.clear()
    }
}