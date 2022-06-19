package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.AlarmItemBinding
import com.healstationlab.design.model.AlarmModel

class AlarmAdapter(private val titleList : ArrayList<AlarmModel>) : RecyclerView.Adapter<AlarmAdapter.Holder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(state : Boolean, position: Int)
    }

    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = AlarmItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return titleList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(titleList[position])

        holder.switch2.setOnCheckedChangeListener { _, b ->
            itemClickListener.onClick(b, position)
        }
    }

    inner class Holder(binding: AlarmItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val textview288 = binding.textView288 // 타이틀
        @SuppressLint("UseSwitchCompatOrMaterialCode")
        val switch2 = binding.switch2

        fun bind(binding : AlarmModel){
            textview288.text = binding.title
            switch2.isChecked = binding.state
        }
    }
}