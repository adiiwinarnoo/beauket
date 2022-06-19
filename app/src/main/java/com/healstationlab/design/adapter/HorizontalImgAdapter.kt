package com.healstationlab.design.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.*
import com.bumptech.glide.request.RequestOptions
import com.healstationlab.design.databinding.ImgItemBinding

class HorizontalImgAdapter(private val imgList : ArrayList<Any>, val gubun : String ="") : RecyclerView.Adapter<HorizontalImgAdapter.Holder>(){


    private lateinit var itemClickListener: ItemClickListener

    var edited = 0
    interface ItemClickListener {
        fun onClick(view: View, position: Int)
        fun onDelete(view : View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = ImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.d("comment-adapter", "adapter:  ${imgList.toString()}")
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
//        Glide.with(holder.itemView.context)
//            .load(imgList[position])
//            .into(holder.img)

        holder.bind(imgList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.imageView114.setOnClickListener {
            itemClickListener.onDelete(it, position)
        }
    }

    inner class Holder(binding: ImgItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val img = binding.img
        val imageView114 = binding.imageView114

        fun bind(img_any : Any){
            Glide.with(itemView).load(img_any).apply(RequestOptions.centerInsideTransform().transform(GranularRoundedCorners(8f, 8f,8f,8f))).into(img)
            if(gubun != "upload"){
                imageView114.isVisible = false
            }
        }
    }
}