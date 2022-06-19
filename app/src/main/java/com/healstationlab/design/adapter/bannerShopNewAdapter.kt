package com.healstationlab.design.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import com.healstationlab.design.R


class bannerShopNewAdapter (val context: Context, var images : IntArray):PagerAdapter() {

    lateinit var layoutInflater : LayoutInflater

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
       return view === `object` as GridLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = layoutInflater.inflate(R.layout.home_viewapger_item,container,false)
        val imageview = view.findViewById<ImageView>(R.id.home_item)
        imageview.setImageResource(images[position])
        container.addView(view)
        return view
    }
    init {
        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
}