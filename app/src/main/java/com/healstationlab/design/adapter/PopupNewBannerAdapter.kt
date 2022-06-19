package com.healstationlab.design.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.model.PopUpNewBanner


class PopupNewBannerAdapter(private val dataSet: List<PopUpNewBanner>):
    RecyclerView.Adapter<PopupNewBannerAdapter.ViewHolder>() {

    private lateinit var itemClickListener: PopupNewBannerAdapter.ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }


    fun setItemClickListner(itemClickListener: PopupNewBannerAdapter.ItemClickListener) {
        this.itemClickListener = itemClickListener
    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val textView2: TextView


        init {
            // Define click listener for the ViewHolder's View.
            textView = view.findViewById(R.id.tv_question)
            textView2 = view.findViewById(R.id.tv_answer)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LayoutInflater.from(parent?.context).inflate(R.layout.list_pop_new_banner,
        parent,false)
        Log.d("isi-array-fragment", dataSet.size.toString())
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Log.d("isi-array-aaa", dataSet[position].textQuestion.toString())
        holder.textView.text = dataSet[position].textQuestion.toString()
        holder.textView2.text = dataSet[position].textAnswer.toString()


    }

    override fun getItemCount(): Int {
       return dataSet.size
    }


}
