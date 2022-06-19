package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.FavoriteItemBinding
import com.healstationlab.design.model.Cart
import java.text.DecimalFormat

class FavoriteAdapter(private val favoriteList : ArrayList<Cart>) : RecyclerView.Adapter<FavoriteAdapter.Holder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
        fun onClose(view : View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FavoriteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return favoriteList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(favoriteList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.close.setOnClickListener {
            itemClickListener.onClose(it, position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class Holder(binding : FavoriteItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val imageView39 = binding.imageView39
        private val ratingBar4 = binding.ratingBar4
        val close = binding.close
        private val textView223 = binding.textView223 // 레이팅 점수
        val brand = binding.brand
        val name = binding.name
        val price = binding.price
        @SuppressLint("SetTextI18n")
        fun bind(binding : Cart){
            ratingBar4.rating = binding.rating!!.toFloat()
            textView223.text = "(${DecimalFormat("#.#").format(binding.rating!!)})"
            brand.text = binding.brand
            name.text = binding.name
            price.text = sliceAmountNumber((binding.price!!))
            Glide.with(itemView).load(binding.imageUrl).into(imageView39)
        }
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}