package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.healstationlab.design.databinding.ReviewImgItemBinding
import com.healstationlab.design.databinding.ReviewItemBinding
import com.healstationlab.design.model.ReviewModel
import com.healstationlab.design.ui.MyFaceActivity

class ReviewAdapter(val reviewList : ArrayList<ReviewModel>) : RecyclerView.Adapter<ReviewAdapter.Holder>() {

//   lateinit var itemClickListener: ReviewAdapter.ItemClickListener
//
//    interface ItemClickListener {
//        fun onClick(view: View, position: Int)
//    }
//
//    fun setItemClickListner(itemClickListener: ItemClickListener) {
//        this.itemClickListener = itemClickListener
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ReviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(reviewList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


//    이사님 등록할때 수다방 디자인두 여쭤봐주실수있으신가여?


    inner class Holder(binding : ReviewItemBinding) : RecyclerView.ViewHolder(binding.root){
        val nickname = binding.textView209
        val recyclerView = binding.recyclerView
        val rating = binding.ratingBar3
        val content = binding.textView219
        private val circleImageView2 = binding.circleImageView2 // 이미지
        private val textView217 = binding.textView217 // 피부문제
        private val textView218 = binding.textView218 // 리뷰 작성 날짜

        @SuppressLint("SetTextI18n")
        fun bind(binding: ReviewModel) {
            var skin = ""
            var type = ""
            var age = ""

            if (binding.imageUrls.isEmpty()){
                recyclerView.isVisible = false
            }

            if(binding.nickname == "null"){
                nickname.text = "닉네임"
            } else {
                nickname.text = binding.nickname
            }
            if(binding.skinProblems?.size != 0 && binding.skinProblems != null){
                for(i in 0 until binding.skinProblems.size){
                    when(binding.skinProblems[i]){
                        "PIMPLE" -> type = "여드름"
                        "SENSITIVITY" -> type = "민감성 피부"
                        "SHADE_SPOT" -> type = "색조반점"
                        "DARK_CIRCLE" -> type = "다크서클"
                        "PORE" -> type = "모공"
                        "BLACK_HEAD" -> type = "블랙헤드"
                        "WRINKLE" -> type = "주름"
                    }
                    skin += " | $type"
                }
            }

            if(binding.birthDate != null){
                val birthSplit = binding.birthDate.split("-")

                when(2021-birthSplit[0].toInt()+1){
                    in 10..19 -> age = "10대"
                    in 20..29 -> age = "20대"
                    in 30..39 -> age = "30대"
                    in 40..49 -> age = "40대"
                    in 50..59 -> age = "50대"
                    in 60..69 -> age = "60대"
                }
            }

            textView217.text = age+skin

            recyclerView.apply {
                adapter = ReviewImageAdapter(reviewList[adapterPosition].imageUrls)
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            }
            rating.rating = binding.rating.toFloat()
            content.text = binding.content
            nickname.text = binding.nickname
            textView218.text = binding.createdDate

            if(binding.imageUrl != null){
                Glide.with(itemView).load(binding.imageUrl).into(circleImageView2)
            }
        }
    }
}

class ReviewImageAdapter(private val reviewList : ArrayList<String>) : RecyclerView.Adapter<ReviewImageAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewImageAdapter.Holder {
        val binding = ReviewImgItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewImageAdapter.Holder, position: Int) {
        holder.bind(reviewList[position])

        holder.img.setOnClickListener {
            val intent = Intent(it.context, MyFaceActivity::class.java)
            intent.putExtra("img", reviewList[position])
            it.context.startActivity(intent)
            // it.context.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }
    }

    inner class Holder(binding: ReviewImgItemBinding) : RecyclerView.ViewHolder(binding.root){
        val img = binding.img

        fun bind(binding: String){
            Glide.with(itemView).load(binding).apply(
                RequestOptions.centerInsideTransform().transform(
                    GranularRoundedCorners(8f, 8f,8f,8f)
                )).into(img)
        }
    }
}