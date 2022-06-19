package com.healstationlab.design.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.databinding.HomeViewapgerItemBinding
import com.healstationlab.design.model.Banner
import com.healstationlab.design.model.PopUpNewBanner
import com.healstationlab.design.ui.ProductDetailActivity

class HomeAdapter(private val imgList : ArrayList<Banner>) : RecyclerView.Adapter<HomeAdapter.Holder>() {

    private lateinit var itemClickListener: ItemClickListener
    var recyclerPopup : RecyclerView? = null
    var isiArray = ArrayList<PopUpNewBanner>()


    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }

    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = HomeViewapgerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return imgList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(imgList[position])
        var isi1 = PopUpNewBanner("먼저, 뷰켓을 만나기 전 피부 관리에 대해서 어떤 생각을 하셨나요?",
            "피부에 특별히 신경을 쓰진 않았어요. 그냥 지저분한 게 보이기 싫어서 쿠션으로 열심히 가리고 다녔어요")
        isiArray.add(isi1)

        holder.itemView.setOnClickListener {
            if(imgList[position].id == 6){
                val inflater = it.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val menuPopup = inflater!!.inflate(R.layout.popup_home_new_onclick,null)
                val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, false)
                popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
                val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
                imgClose.setOnClickListener { popup.dismiss() }

            }else if (imgList[position].id == 8){
                val inflater = it.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val menuPopup = inflater!!.inflate(R.layout.popup_home_new_onclick,null)
                val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, false)
                popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
                val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
                imgClose.setOnClickListener { popup.dismiss() }
            }else if (imgList[position].id == 9){
                val inflater = it.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
                val menuPopup = inflater!!.inflate(R.layout.new_popup_banner_home,null)
                val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.MATCH_PARENT, false)
                popup.isFocusable = true
                popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
                val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
                val buttonSubmit = menuPopup.findViewById<TextView>(R.id.confirm_text)
                imgClose.setOnClickListener { popup.dismiss() }
                buttonSubmit.setOnClickListener { popup.dismiss() }
            }
            /**Popup With QA 90**/
            else if(imgList[position].id == 90){

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

            }else if(imgList[position].id == 91){
                /**Popup With QA 91**/
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
            }

            if (imgList[position].linkUrl != null && imgList[position].linkUrl != "") {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(imgList[position].linkUrl)
                it.context.startActivity(intent)
            } else if (imgList[position].product != null) {
                val intent = Intent(it.context, ProductDetailActivity::class.java)
                intent.putExtra("id", imgList[position].product!!.id)
                it.context.startActivity(intent)
            }
        }
    }

    inner class Holder(binding : HomeViewapgerItemBinding) : RecyclerView.ViewHolder(binding.root) {

        val item = binding.homeItem

        fun bind(img : Banner) {
                Glide.with(itemView).load(img.imageUrl).into(item)

        }


    }

}