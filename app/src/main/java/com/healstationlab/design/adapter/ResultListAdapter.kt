package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.ResultListItemBinding

class ResultListAdapter(val list : ArrayList<String>) : RecyclerView.Adapter<ResultListAdapter.Holder>() {

    private lateinit var itemClickListener: ItemClickListener
    var clicked : Int = 0

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ResultListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])

        holder.detail.setOnClickListener {
            Log.d("clicked", "onBindViewHolder: ${clicked.toString()} ")
            holder.detail.visibility = View.GONE
            holder.detail2.visibility = View.VISIBLE
            clicked++
            if (clicked == 2){
                itemClickListener.onClick(it, position)
            }

        }
    }

    inner class Holder(binding : ResultListItemBinding) : RecyclerView.ViewHolder(binding.root){
        val number = binding.number
        val date = binding.date
        val detail = binding.detail
        val detail2 = binding.detail2

        @SuppressLint("SetTextI18n")
        fun bind(binding : String){
            number.text = (adapterPosition + 1).toString()
            date.text = binding
        }
    }
}