@file:Suppress("UNREACHABLE_CODE")

package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.healstationlab.design.R
import com.healstationlab.design.model.Product
import com.healstationlab.design.databinding.RecommendItemBinding
import com.healstationlab.design.databinding.SaleItemBinding
import java.text.DecimalFormat

class ProductAdapter(val product : ArrayList<Product>, val type : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    companion object {
        const val VERTICAL = 0
        const val HORIZONTAL = 1
        const val GRID = 2
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            VERTICAL -> {
                val binding = RecommendItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return VerticalHolder(binding)
            }

            HORIZONTAL -> {
                val binding = SaleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return HorizontalHolder(binding)
            }

            GRID -> {
                val binding = SaleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return GridHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(type){
            0 -> VERTICAL
            1 -> HORIZONTAL
            else -> GRID
        }
    }

    override fun getItemCount(): Int {
        return product.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is VerticalHolder -> {
                holder.bind(product[position])
            }
            is HorizontalHolder -> holder.bind(product[position])
            is GridHolder -> holder.bind(product[position])
        }

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }
    inner class VerticalHolder(binding : RecommendItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val productimg = binding.productImg
        private val productratingnum = binding.productRatingNum
        private val productrating = binding.productRating
        private val productcompany = binding.productCompany
        private val producttitle = binding.productTitle
        private val productprice = binding.productPrice

        @SuppressLint("SetTextI18n")
        fun bind(product: Product){
            Glide.with(itemView).load(product.product_img).apply(
                RequestOptions.centerInsideTransform().transform(
                    CenterCrop(), GranularRoundedCorners(8f, 8f,8f,8f)
                )).into(productimg)
            //product_img.setImageURI(product.product_img.toUri())
            productratingnum.text = "(${DecimalFormat("#.#").format(product.product_rating_num)})"
            productrating.rating = product.product_rating_num.toFloat()
            productcompany.text = "[${product.product_company}]"
            producttitle.text = product.product_title
            if(product.product_price == ""){
                productprice.text = ""
            } else {
                productprice.text = sliceAmountNumber(product.product_price)
            }
        }
    }

    inner class HorizontalHolder(binding : SaleItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val productimg = binding.productImg
        private val productratingnum = binding.productRatingNum
        private val productrating = binding.productRating
        private val productcompany = binding.productCompany
        private val producttitle = binding.productTitle
        private val productprice = binding.productPrice
        private val rankbackground = binding.rankBackground
        private val ranktextview = binding.rankTextview

        @SuppressLint("SetTextI18n")
        fun bind(product: Product){
            rankbackground.isVisible = false
            ranktextview.isVisible = false
            Glide.with(itemView).load(product.product_img).apply(
                RequestOptions.centerInsideTransform().transform(
                    CenterCrop(), GranularRoundedCorners(8f, 8f,8f,8f)
                )).into(productimg)
            //product_img.setImageURI(product.product_img.toUri())
            productratingnum.text = "(${DecimalFormat("#.#").format(product.product_rating_num)})"
            productrating.rating = product.product_rating_num.toFloat()
            productcompany.text = "[${product.product_company}]"
            producttitle.text = product.product_title
            if(product.product_price == ""){
                productprice.text = ""
            } else {
                productprice.text = sliceAmountNumber(product.product_price)
            }
        }
    }

    inner class GridHolder(binding : SaleItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val productimg = binding.productImg
        private val productratingnum = binding.productRatingNum
        private val productrating = binding.productRating
        private val productcompany = binding.productCompany
        private val producttitle = binding.productTitle
        private val productprice = binding.productPrice
        private val rankbackground = binding.rankBackground
        private val ranktextview = binding.rankTextview

        @SuppressLint("SetTextI18n")
        fun bind(product: Product){
            if(adapterPosition == 0){
                rankbackground.setImageResource(R.drawable.rank_background_green)
            }
            ranktextview.text = (adapterPosition + 1).toString()
            Glide.with(itemView).load(product.product_img).apply(
                RequestOptions.centerInsideTransform().transform(
                    CenterCrop(), GranularRoundedCorners(8f, 8f,8f,8f)
                )).into(productimg)
            //product_img.setImageURI(product.product_img.toUri())
            productratingnum.text = "(${DecimalFormat("#.#").format(product.product_rating_num)})"
            productrating.rating = product.product_rating_num.toFloat()
            productcompany.text = "[${product.product_company}]"
            producttitle.text = product.product_title
            if(product.product_price == ""){
                productprice.text = ""
            } else {
                productprice.text = sliceAmountNumber(product.product_price)
            }
        }
    }

    fun sliceAmountNumber(number : String) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number.toLong())
    }
}