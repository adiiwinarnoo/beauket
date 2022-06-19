package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.DetailOrderItemBinding
import com.healstationlab.design.model.Cart
import java.text.DecimalFormat

class DetailOrderAdapter(val context: Context, private val cartList: ArrayList<Cart>) : RecyclerView.Adapter<DetailOrderAdapter.Holder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DetailOrderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(cartList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class Holder(binding : DetailOrderItemBinding) : RecyclerView.ViewHolder(binding.root){
        val merchant = binding.merchant
        private val shippingMent = binding.shippingMent
        private val imgUrl = binding.imgUrl
        val brand = binding.brand
        val name = binding.name
        val price = binding.price
        val count = binding.count
        val option = binding.textView417

        @SuppressLint("SetTextI18n")
        fun bind(binding : Cart){
            merchant.text = binding.merchantName
            if(binding.shippingPrice == 0){
                shippingMent.text = "배송비 무료"
            } else {
                shippingMent.text = "배송비 " + sliceAmountNumber(binding.shippingPrice.toString())
            }
            Glide.with(itemView).load(binding.imageUrl).into(imgUrl)
            option.text = binding.optionName
            brand.text = "${binding.brand}"
            name.text = binding.name
            price.text = sliceAmountNumber(binding.price.toString())
            count.text = binding.count.toString()+"개"
        }
    }

    fun sliceAmountNumber(number : String) : String {
        val number2 = number.toLong()
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number2)+"원"
    }
}