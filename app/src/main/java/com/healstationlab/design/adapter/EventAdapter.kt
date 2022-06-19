package com.healstationlab.design.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.EventItemBinding
import com.healstationlab.design.model.TestEvent

class EventAdapter(private val eventList : ArrayList<TestEvent>, val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder) {
            is ViewHolder -> {
                holder.bind(eventList[position])

                holder.bannerimg.setOnClickListener {
                    itemClickListener.onClick(holder.itemView, position)
                }

            }
        }
    }

    inner class ViewHolder(view : EventItemBinding) : RecyclerView.ViewHolder(view.root){
        val bannerimg = view.bannerImg
        val eventname  = view.eventTitle
        val start  = view.startDate
        val end  = view.endDate

        fun bind(binding : TestEvent){
            Glide.with(itemView).load(binding.imageUrl).into(bannerimg)
            eventname.text = binding.name
            start.text = binding.startDate
            end.text = binding.endDate
        }
    }
}