package com.healstationlab.design.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CategoryAdapter.Companion.activity
import com.healstationlab.design.databinding.CategoryItemBinding
import com.healstationlab.design.databinding.ViepagerCategoryItemBinding
import com.healstationlab.design.dto.CategoryData
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.ui.CategoryProductActivity
import com.healstationlab.design.ui.SearchActivity


class CategoryAdapter(private val categoryList : ArrayList<ArrayList<CategoryData>>, val gubun : String, val mActivity : Activity)
    : RecyclerView.Adapter<CategoryAdapter.Holder>() {


    companion object {
        var activity : SearchActivity? = null
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ViepagerCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(categoryList[position])
    }

    inner class Holder(binding: ViepagerCategoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        val recyclerview = binding.viewpagerRecyclerView

        fun bind(arrayList : ArrayList<CategoryData>){
            recyclerview.apply {
                adapter = CategoryItemAdapter(arrayList, gubun, mActivity)
                layoutManager = GridLayoutManager(context, 4)
            }
        }
    }
}

class CategoryItemAdapter(val category : ArrayList<CategoryData>, private val gubun : String, private val   mActivity : Activity) : RecyclerView.Adapter<CategoryItemAdapter.Holder>(){

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return category.size
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(category[position])


        holder.itemView.setOnClickListener {
            if(gubun == "0"){
                if(!holder.imageView58.isFocusable){
                    Constant.categoryId += category[position].id.toString()+","
                    holder.imageView58.setBackgroundResource(R.drawable.categort_check)
                    holder.imageView58.isFocusable = true
                    activity = mActivity as SearchActivity
                    activity!!.getRecommendAi(mActivity.binding.editTextTextPersonName5.text.toString())

                } else {
                    Constant.categoryId = Constant.categoryId.replace("${category[position].id},","")
                    holder.imageView58.setBackgroundResource(R.drawable.detail_backgorund)
                    holder.imageView58.isFocusable = false

                }
            } else {
//                Toast.makeText(mActivity, "yey", Toast.LENGTH_SHORT).show()
                val intent = Intent(it.context, CategoryProductActivity::class.java)
                intent.putExtra("title", category[position].name)
                intent.putExtra("id", category[position].id)
                it.context.startActivity(intent)
                mActivity.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            }
        }




    }

    inner class Holder(binding : CategoryItemBinding) : RecyclerView.ViewHolder(binding.root){
        val imageView58 = binding.imageView58
        val name = binding.name
        val img = binding.img

        fun bind(item : CategoryData){
            name.text = item.name
            Glide.with(itemView).load(item.iconImageUrl).into(img)
            if(imageView58.isFocusable){
                imageView58.setBackgroundResource(R.drawable.categort_check)
            } else {
                imageView58.setBackgroundResource(R.drawable.detail_backgorund)
            }
        }
    }
}
