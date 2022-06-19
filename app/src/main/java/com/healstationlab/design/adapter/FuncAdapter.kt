package com.healstationlab.design.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.FuncItemBinding
import com.healstationlab.design.model.Func

data class FuncAdapter(val arrayList : ArrayList<Func>) : RecyclerView.Adapter<FuncAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FuncItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position])
    }

    inner class Holder(binding: FuncItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val imageView67 = binding.imageView67 // 이미지
        private val textView113 = binding.textView113 // 이름

        fun bind(binding: Func){
            Glide.with(itemView).load(binding.imageUrl).into(imageView67)
            textView113.text = binding.name
        }
    }
}