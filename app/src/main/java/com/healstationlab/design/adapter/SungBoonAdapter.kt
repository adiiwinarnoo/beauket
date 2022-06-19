package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.SungboonItemBinding

data class SungBoonAdapter(val list : ArrayList<String>) : RecyclerView.Adapter<SungBoonAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SungboonItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(list[position])
    }

    inner class Holder(binding: SungboonItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val sungBoon = binding.sungBoon
        private val pos = binding.pos

        @SuppressLint("SetTextI18n")
        fun bind(binding : String){
            pos.text = (adapterPosition+1).toString()
            sungBoon.text = binding
        }
    }
}