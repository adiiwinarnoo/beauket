package com.healstationlab.design.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.InqueryItemBinding
import com.healstationlab.design.fragment_nesting.InqueryFragment
import com.healstationlab.design.model.Inquery

class InqueryAdapter(private val inqueryList: List<Inquery>, val fragment: InqueryFragment? = null) : RecyclerView.Adapter<InqueryAdapter.Holder>() {


    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: TextView, view2 : TextView, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = InqueryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {

        return inqueryList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        holder.bind(inqueryList[position])

        holder.itemView.setOnClickListener {

            if(holder.repliedyn.text.toString() == "답변완료"){
                itemClickListener.onClick(holder.reply, holder.textView184, position)
                if(holder.detailbutton.isClickable){
                    holder.replyview.isVisible = true
                    holder.detailbutton.setBackgroundResource(R.drawable.arrow_up)
                    holder.detailbutton.isClickable = false
                }
                else {
                    holder.replyview.isVisible = false
                    holder.detailbutton.setBackgroundResource(R.drawable.down_arrow)
                    holder.detailbutton.isClickable = true
                }
            }
        }
    }

    inner class Holder(binding: InqueryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val repliedyn = binding.repliedYn
        val name = binding.name
        val detailbutton = binding.detailButton
        val replyview = binding.replyView
        val content = binding.replyContent
        var reply = binding.reply
        private val textView181 = binding.textView181 // 문의한날짜
        val textView184 = binding.textView184 // 답변날짜

        fun bind(binding : Inquery){
            if(binding.answered){
                repliedyn.text = "답변완료"
                repliedyn.setTextColor(Color.parseColor("#5A2D8B"))
            } else {
                repliedyn.setTextColor(Color.parseColor("#727272"))
                repliedyn.text = "답변 미완료"
            }
            when(binding.type){
                "SHOPPING" -> {name.text = "[만세 쇼핑 문의]"}
                "SKIN" -> {name.text = "[피부 분석 문의]"}
                "EVENT" -> {name.text = "[이벤트 문의]"}
                "SERVICE" -> {name.text = "[서비스 불편, 오류 제보]"}
                "ETC" -> {name.text = "[사용방법, 기타 문의]"}
                "PROPOSAL" -> {name.text = "[아이디어 제안, 칭찬]"}
                "AFFILIATE" -> {name.text = "[제휴 문의]"}
            }
            content.text = binding.content
            textView181.text = binding.createdDate
        }
    }
}