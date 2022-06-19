package com.healstationlab.design.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.FaqItemBinding
import com.healstationlab.design.dto.faq

class FAQAdapter(private val publicList : ArrayList<faq.Data>) : RecyclerView.Adapter<FAQAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return publicList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(publicList[position])

        holder.itemView.setOnClickListener {
            if (holder.detailbutton.isFocusable) {
                holder.answer.isVisible = true
                holder.detailbutton.isFocusable = false
                holder.detailbutton.setBackgroundResource(R.drawable.arrow_up)
            } else {
                holder.answer.isVisible = false
                holder.detailbutton.isFocusable = true
                holder.detailbutton.setBackgroundResource(R.drawable.down_arrow)
            }
        }
    }

    inner class Holder(binding: FaqItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val question = binding.question
        val answer = binding.answer
        val detailbutton = binding.detailButton

        fun bind(binding: faq.Data) {
            question.text = binding.question
            answer.text = binding.answer
        }
    }
}