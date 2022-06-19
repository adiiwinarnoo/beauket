package com.healstationlab.design.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.HomeViewapgerItemBinding

class AllFirstAdapter (private val imgList : ArrayList<Int>, val gubun : String) : RecyclerView.Adapter<AllFirstAdapter.Holder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllFirstAdapter.Holder {
        val binding = HomeViewapgerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: AllFirstAdapter.Holder, position: Int) {
        holder.bind(imgList[position])
    }

    inner class Holder(binding : HomeViewapgerItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val item = binding.homeItem
        val img = binding.img

        fun bind(img : Int) {
            if(gubun == "second"){
                this.img.radius = 0.0f
            }

            item.setImageResource(img)
        }
    }
}