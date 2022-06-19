package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.PublicNoticeItemBinding
import com.healstationlab.design.dto.PublicNotice

class PublicNoticeAdapter(private val publicList : ArrayList<PublicNotice.PublicData>) : RecyclerView.Adapter<PublicNoticeAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = PublicNoticeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return publicList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(publicList[position])

        holder.itemView.setOnClickListener {
            if(holder.detailbutton.isFocusable){
                holder.content.isVisible = true
                holder.detailbutton.isFocusable = false
                holder.detailbutton.setBackgroundResource(R.drawable.arrow_up)
            } else {
                holder.content.isVisible = false
                holder.detailbutton.isFocusable = true
                holder.detailbutton.setBackgroundResource(R.drawable.down_arrow)
            }
        }
    }

    inner class Holder(binding : PublicNoticeItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.question
        val date = binding.date
        val detailbutton = binding.detailButton
        val content = binding.content

        @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
        fun bind(binding : PublicNotice.PublicData){
            title.text = binding.title
            date.text = "2021.01.20"
            content.settings.javaScriptEnabled = true
            content.settings.useWideViewPort = true
            content.settings.loadWithOverviewMode = true
            content.settings.builtInZoomControls = true
            content.settings.setSupportZoom(true)
            content.settings.displayZoomControls = false
            content.settings.builtInZoomControls = true
            content.settings.displayZoomControls = true
            content.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
            content.loadData(binding.contents.replace("860", "1280"), "text/html", "UTF-8")
        }
    }
}