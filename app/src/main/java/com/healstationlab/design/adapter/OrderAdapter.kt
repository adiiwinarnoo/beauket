package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.OrderItemBinding
import com.healstationlab.design.model.myOrderListModel
import java.text.DecimalFormat

class OrderAdapter(var arrayList : ArrayList<myOrderListModel>) : RecyclerView.Adapter<OrderAdapter.Holder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = OrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(arrayList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class Holder(binding : OrderItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val textView171 = binding.textView171 // 주문번호
        private val textView177 = binding.textView177 // 구매날짜
        private val imageView36 = binding.imageView36 // 상품 이미지
        private val textView178 = binding.textView178 // 상품 이름
        private val textView179 = binding.textView179 // 상품 가격

        @SuppressLint("SetTextI18n")
        fun bind(binding : myOrderListModel){


            val split = binding.create
            textView171.text = binding.orderNo
            textView177.text = "${split[0]}.${split[1]}.${split[2]}"
            Glide.with(itemView).load(binding.imgUrl).into(imageView36)
            textView178.text = binding.name
            textView179.text = sliceAmountNumber(binding.price.toString())+"원"
        }
    }

    fun sliceAmountNumber(number : String) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number.toLong())
    }
}