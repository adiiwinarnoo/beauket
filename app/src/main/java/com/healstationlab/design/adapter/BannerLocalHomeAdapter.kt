package com.healstationlab.design.adapter

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.model.BannerLocal
import com.healstationlab.design.model.PopUpNewBanner

class BannerLocalHomeAdapter(private val imageList: ArrayList<BannerLocal>)
    :RecyclerView.Adapter<BannerLocalHomeAdapter.ViewHolder>(){

    private lateinit var itemClickListener: BannerLocalHomeAdapter.ItemClickListener
    var isiArray = ArrayList<PopUpNewBanner>()


    interface ItemClickListener {
        fun onClick(view: View,position: Int)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val imageView = itemView.findViewById<ImageView>(R.id.home_item)
        fun bind(image:BannerLocal) {
            Glide.with(itemView).load(image.getImage()).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.home_viewapger_item,
            parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(imageList[position])
        var isi1 = PopUpNewBanner("먼저, 뷰켓을 만나기 전 피부 관리에 대해서 어떤 생각을 하셨나요?",
            "피부에 특별히 신경을 쓰진 않았어요. 그냥 지저분한 게 보이기 싫어서 쿠션으로 열심히 가리고 다녔어요")
        isiArray.add(isi1)
        isiArray.add(isi1)
        isiArray.add(isi1)
        isiArray.add(isi1)
        holder.itemView.setOnClickListener {
            if (imageList[position].getIdImage() == 10){
                val inflater = it.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val menuPopup = inflater!!.inflate(R.layout.popup_new_banner_home,null)
                val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, false)
                val adapter = PopupNewBannerAdapter(
                    isiArray)
                val recycleQuesAns = menuPopup.findViewById<RecyclerView>(R.id.recycler_new_popup_banner_shopping)
                recycleQuesAns?.adapter = adapter
                recycleQuesAns?.layoutManager = LinearLayoutManager(it.context)
                popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
                val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
                val buttonSubmit = menuPopup.findViewById<TextView>(R.id.confirm_text)
                imgClose.setOnClickListener { popup.dismiss() }
                buttonSubmit.setOnClickListener { popup.dismiss() }
            }else if (imageList[position].getIdImage() == 11){
                val inflater = it.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val menuPopup = inflater!!.inflate(R.layout.popup_new_banner_2,null)
                val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, false)
                val adapter = PopupNewBannerAdapter(
                    isiArray)
                val recycleQuesAns = menuPopup.findViewById<RecyclerView>(R.id.recycler_new_popup_banner_shopping)
                recycleQuesAns?.adapter = adapter
                recycleQuesAns?.layoutManager = LinearLayoutManager(it.context)
                popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
                val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
                val buttonSubmit = menuPopup.findViewById<TextView>(R.id.confirm_text)
                imgClose.setOnClickListener { popup.dismiss() }
                buttonSubmit.setOnClickListener { popup.dismiss() }
            }else{

            }
        }
    }

    override fun getItemCount(): Int {
      return imageList.size
    }
}