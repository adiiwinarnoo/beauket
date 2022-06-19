package com.healstationlab.design.adapter

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.WordCountItemBinding
import com.healstationlab.design.model.countModel

class WordReviewAdapter(val check : List<countModel>) : RecyclerView.Adapter<WordReviewAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = WordCountItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return check.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(check[position])
    }

    inner class Holder(binding: WordCountItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val textView264 = binding.textView264

        fun bind(binding: countModel){
            if(check[adapterPosition].count != 0){ // 0 filter
                textView264.text = binding.word
                if(check[adapterPosition].rankFrist){
                    textView264.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
                    textView264.setTextColor(Color.WHITE)
                    textView264.elevation = 5F
                    textView264.setPadding(0, 30, 0, 30)
                } else if(check[adapterPosition].rankSecondThird){
                    textView264.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
                    textView264.setTextColor(Color.WHITE)
                    textView264.elevation = 5F
                    textView264.setPadding(0, 15, 0, 15)
                }
            } else {
                textView264.isVisible = false
            }
        }
    }
}