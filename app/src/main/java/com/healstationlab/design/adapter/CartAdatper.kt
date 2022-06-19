@file:Suppress("UNREACHABLE_CODE")

package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.CartViewtype1ItemBinding
import com.healstationlab.design.databinding.CartViewtype2ItemBinding
import com.healstationlab.design.model.Cart
import java.text.DecimalFormat

class CartAdatper(private val cartArrayList : ArrayList<Cart>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
        fun onCloseClick(view : View, position: Int)
        fun onMinusClick(view : View, position: Int)
        fun onPlusClick(view: View, position: Int)
        fun allClick(boolean : Boolean, position: Int)
        fun selectDelete(view : View, position: Int)
        fun checkBox(view : View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    companion object {
        const val FIRST = 0
        const val SECOND = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when(position){
            0 -> FIRST
            else -> SECOND
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FIRST -> {
                val binding = CartViewtype1ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return FirstHolder(binding)
            }
            else -> {
                val binding = CartViewtype2ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return SecondHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is SecondHolder -> {
                holder.bind(cartArrayList[position-1])

                holder.close.setOnClickListener {
                    itemClickListener.onCloseClick(it, position)
                }

                holder.minus.setOnClickListener {
                    if(holder.cnt.text.toString().toInt() != 1){
                        itemClickListener.onMinusClick(it, position)
                        holder.cnt.text = holder.cnt.text.toString().toInt().minus(1).toString()
                        holder.price.text = sliceAmountNumber(holder.price.text.toString().replace(",","").toInt().minus(cartArrayList[position-1].price!!))
                    }
                }

                holder.plus.setOnClickListener {
                    itemClickListener.onPlusClick(it, position)
                    holder.cnt.text = holder.cnt.text.toString().toInt().plus(1).toString()
                    holder.price.text = sliceAmountNumber(holder.price.text.toString().replace(",","").toInt().plus(cartArrayList[position-1].price!!))
                }

                holder.checkBox.setOnClickListener {
                    itemClickListener.checkBox(it, position-1)
                }

                holder.itemView.setOnClickListener {
                    itemClickListener.onClick(it, position)
                }
            }

            is FirstHolder -> {
                holder.allCheckBox.setOnCheckedChangeListener { _, b ->
                    itemClickListener.allClick(b, position)
                }

                holder.selectDelete.setOnClickListener {
                    itemClickListener.selectDelete(it, position)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return cartArrayList.size.plus(1)
    }

    inner class SecondHolder(binding : CartViewtype2ItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val imageView39 = binding.imageView39
        val checkBox = binding.checkBox
        val cnt = binding.cnt
        private val ratingBar4 = binding.ratingBar4
        val close = binding.close
        private val textView223 = binding.textView223 // 레이팅 점수
        val brand = binding.brand
        val name = binding.name
        val price = binding.price
        val minus = binding.minus
        val plus = binding.plus

        @SuppressLint("SetTextI18n")
        fun bind(binding : Cart){
            checkBox.isChecked = binding.isChecked!!
            ratingBar4.rating = binding.rating!!.toFloat()
            textView223.text = "(${DecimalFormat("#.#").format(binding.rating!!)})"
            brand.text = "[${binding.brand}]"
            name.text = binding.name
            price.text = sliceAmountNumber((binding.price!! * binding.count!!))
            cnt.text = binding.count.toString()
            Glide.with(itemView).load(binding.imageUrl).into(imageView39)
        }
    }

    inner class FirstHolder(binding : CartViewtype1ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val allCheckBox = binding.checkBox2
        val selectDelete = binding.textView222


//        fun bind(){
//            when(allCheckBox.isChecked){
//                true -> {
//                    for(i in 0..cartArrayList.size-1){
//                        cartArrayList[i].isChecked = true
//                    }
//                    notifyDataSetChanged()
//                }
//                false -> {
//                    for(i in 0..cartArrayList.size-1){
//                        cartArrayList[i].isChecked = false
//                    }
//                    notifyDataSetChanged()
//                }
//            }
//        }
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}