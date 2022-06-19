package com.healstationlab.design.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.model.WelcomeScreen

class WelcomeScreenAdapter(private val imageList : ArrayList<WelcomeScreen> )
    :RecyclerView.Adapter<WelcomeScreenAdapter.ViewHolder>() {


    private lateinit var itemClickListener: WelcomeScreenAdapter.ItemClickListener
    public lateinit var btnLogin : Button
    public lateinit var viewPager : ViewPager2

    interface ItemClickListener {
        fun onClick(view: View,position: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.home_item)
        fun bind(image: WelcomeScreen) {
            Glide.with(itemView).load(image.getImage()).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.welcome_screen,parent,false)
        btnLogin = view.findViewById(R.id.btnLoginScreen)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
        btnLogin.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}