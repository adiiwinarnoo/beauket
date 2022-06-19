package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.CouponItemBinding
import com.healstationlab.design.dto.coupon
import java.text.DecimalFormat

class CouponAdapter(private val couponList : ArrayList<coupon.Data.Meta>, val price : Long = 0) : RecyclerView.Adapter<CouponAdapter.Holder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int, focus : Boolean)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CouponItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return couponList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(couponList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position, holder.backgroundview.isFocusable)
        }
    }

    inner class Holder(binding : CouponItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val expirationdatetextview = binding.expirationDateTextview
        val backgroundview = binding.backGroundView
        val name = binding.name
        private val discount = binding.discount
        private val minimumPrice = binding.minimumPrice


        @SuppressLint("SetTextI18n")
        fun bind(binding : coupon.Data.Meta) {
            if(price.toInt() == 0){
                expirationdatetextview.text = binding.expiredDate+"까지 사용가능"
                name.text = binding.name
                discount.text = "${sliceAmountNumber(binding.discountPrice.toLong())}원 할인"
                minimumPrice.text = "${sliceAmountNumber(binding.minimumPrice.toLong())}원 이상 사용가능"
            }else if (price <= binding.minimumPrice){
                backgroundview.isFocusable = true
                backgroundview.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#C2C2C2"))
                minimumPrice.text = "${sliceAmountNumber(binding.minimumPrice.toLong())}원 이상 사용가능"
                backgroundview.isFocusable = true
            }
            expirationdatetextview.text = binding.expiredDate+"까지 사용가능"
            name.text = binding.name
            discount.text = "${sliceAmountNumber(binding.discountPrice.toLong())}원 할인"
            minimumPrice.text = "${sliceAmountNumber(binding.minimumPrice.toLong())}원 이상 사용가능"
        }
    }
    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}