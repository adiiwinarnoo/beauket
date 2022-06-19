package com.healstationlab.design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.model.Address
import com.healstationlab.design.databinding.AddressItemBinding

class AddressAdapter(private val addressList : ArrayList<Address>) : RecyclerView.Adapter<AddressAdapter.Holder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AddressItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
    }

    inner class Holder(binding : AddressItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val roadAddr = binding.roadAddr
        private val jibunAddr = binding.jibunAddr
        private val zipNo = binding.zipNo

        fun bind(addr : Address){
            roadAddr.text = addr.roadAddr
            jibunAddr.text = addr.jibunAddr
            zipNo.text = addr.zipNo
        }
    }
}