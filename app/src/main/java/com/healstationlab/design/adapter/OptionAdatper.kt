package com.healstationlab.design.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.databinding.OptionItemBinding
import com.healstationlab.design.model.Option
import java.text.DecimalFormat

class OptionAdatper(private val optionList : ArrayList<Option>) : RecyclerView.Adapter<OptionAdatper.Holder>() {

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
        fun plusClick(view : View, position: Int)
        fun minusClick(view : View, position: Int)
        fun delete(view : View, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = OptionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        // Log.d("option size : ", )
        return optionList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(optionList[position])

        holder.plus.setOnClickListener {
            holder.cnt.text = holder.cnt.text.toString().toInt().plus(1).toString()
            itemClickListener.plusClick(it, position)
        }

        holder.minus.setOnClickListener {
            itemClickListener.minusClick(it, position)
            if(holder.cnt.text.toString() != "1"){
                holder.cnt.text = holder.cnt.text.toString().toInt().minus(1).toString()
            }
        }

        holder.delete.setOnClickListener {
            itemClickListener.delete(it, position)
        }
    }

    inner class Holder(binding : OptionItemBinding) : RecyclerView.ViewHolder(binding.root){
        val delete = binding.delete
        val name = binding.name
        val price = binding.price
        val plus = binding.plus
        val minus = binding.minus
        val cnt = binding.cnt

        fun bind(binding : Option){
            name.text = binding.name
            price.text = sliceAmountNumber(binding.price)
        }
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)+"원"
    }
}