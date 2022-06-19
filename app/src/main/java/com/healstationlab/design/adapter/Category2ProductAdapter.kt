package com.healstationlab.design.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.CategoryProductItem2Binding
import com.healstationlab.design.model.CategoryProductModel

class Category2ProductAdpater(private val caegoryList : ArrayList<CategoryProductModel>) : RecyclerView.Adapter<Category2ProductAdpater.Holder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CategoryProductItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return caegoryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(caegoryList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class Holder(binding: CategoryProductItem2Binding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.textView196

        fun bind(binding : CategoryProductModel){
            title.text = binding.name

            when(binding.isClick){
                true -> {
                    title.setTextColor(Color.parseColor("#5A2D8B"))
                }
                false -> {
                    title.setTextColor(Color.parseColor("#777777"))
                }
            }
        }
    }
}