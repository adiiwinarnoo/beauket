package com.healstationlab.design.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.CategoryProductItemBinding
import com.healstationlab.design.model.CategoryProductModel

class CategoryProductAdpater(private val caegoryList : ArrayList<CategoryProductModel>) : RecyclerView.Adapter<CategoryProductAdpater.Holder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CategoryProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    inner class Holder(binding: CategoryProductItemBinding) : RecyclerView.ViewHolder(binding.root){
        val title = binding.textView195
        private val under = binding.view128

        fun bind(binding : CategoryProductModel){
            title.text = binding.name

            when(binding.isClick){
                true -> {
                    title.setTextColor(Color.parseColor("#5A2D8B"))
                    under.setBackgroundColor(Color.parseColor("#5A2D8B"))
                }
                false -> {
                    title.setTextColor(Color.parseColor("#777777"))
                    under.setBackgroundColor(Color.parseColor("#00000000"))
                }
            }
        }
    }
}