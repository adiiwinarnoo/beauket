package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.DeliveryItemBinding
import com.healstationlab.design.model.MyAddress
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant

class DeliveryAdapter(private val addressList : ArrayList<MyAddress>) : RecyclerView.Adapter<DeliveryAdapter.Holder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
        fun onDeleteClick(view : View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = DeliveryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(addressList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.delete.setOnClickListener {
            itemClickListener.onDeleteClick(it, position)
        }
    }

    inner class Holder(binding: DeliveryItemBinding) : RecyclerView.ViewHolder(binding.root){
        var name = binding.name
        val phone = binding.phone
        private val addressCode = binding.addressCode
        private val detail = binding.detail
        private val default = binding.defaultDel
        val address = binding.address
        val delete = binding.delete
        val background = binding.background

        @SuppressLint("SetTextI18n")
        fun bind(binding : MyAddress){
            detail.text = binding.addressDetail
            addressCode.text = "(${binding.addressCode})"
            name.text = binding.name
            phone.text = binding.contact
            address.text = binding.address

            if(adapterPosition == App.prefs.getIntData(Constant.DEFAULT_POSITION)){
                default.isVisible = true
                background.setBackgroundResource(R.drawable.stroke_green)
            }
        }
    }
}