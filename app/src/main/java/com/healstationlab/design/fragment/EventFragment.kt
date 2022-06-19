package com.healstationlab.design.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.adapter.EventAdapter
import com.healstationlab.design.dto.event
import com.healstationlab.design.model.TestEvent
import com.healstationlab.design.resource.ProgressDialog
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.EventDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventFragment : Fragment() {
    var context2 : Context? = null
    var imgList : ArrayList<TestEvent> = arrayListOf()
    lateinit var eventRecyclerView : RecyclerView

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_event, container, false)
        eventRecyclerView = view!!.findViewById(R.id.eventRecyclerView)
        getEvent()

        return view
    }

    private fun getEvent(){
        val dialog= ProgressDialog.progressDialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        RetrofitMansae.server.getEvent()
            .enqueue(object : Callback<event>{
                override fun onFailure(call: Call<event>, t: Throwable) {

                }

                override fun onResponse(call: Call<event>, response: Response<event>) {

                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {

                            for(i in response.body()!!.data){
                                imgList.add(
                                    TestEvent(
                                            id = i.id, name = i.name, details = i.details, imageUrl = i.imageUrl, startDate = i.startDate, endDate = i.endDate
                                    )
                                )
                            }
                            val eventAdapter = EventAdapter(imgList, context2!!)

                            eventRecyclerView.apply {
                                adapter = eventAdapter
                                layoutManager = LinearLayoutManager(context2)
                            }

                            eventAdapter.setItemClickListner(object : EventAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, EventDetailActivity::class.java)
                                    intent.putExtra("id", imgList[position].id)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                }
                            })
                            dialog.dismiss()
                        }
                        else -> {
                            dialog.dismiss()
                        }
                    }
                }
            })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context2 = context
    }

    override fun onDetach() {
        super.onDetach()
        imgList.clear()
    }
}