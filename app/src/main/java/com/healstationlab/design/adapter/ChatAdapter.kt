package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.model.Chat
import com.healstationlab.design.databinding.ChatItemBinding

class ChatAdapter(private val chatList : ArrayList<Chat>) : RecyclerView.Adapter<ChatAdapter.Holder>(){

    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onClick(view: View, skin : String, position: Int)
    }
    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(chatList[position])

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, holder.userinfotext.text.toString(), position)
        }
    }


    inner class Holder(binding : ChatItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private val profileimg = binding.profileImg
        private val nicknametext = binding.nickNameText
        val userinfotext = binding.userInfoText
        private val registerdate = binding.registerDate
        private val contenttext = binding.contentText
//        val show_all_text = binding.showAllText

        @SuppressLint("SetTextI18n")
        fun bind(chat : Chat) {
            var skin = ""
            var type: String
            var age = ""
            val split = chat.createdDate?.split(" ")
            if (chat.nickname == "null") {
                nicknametext.text = "닉네임"
            } else {
                nicknametext.text = chat.nickname
            }
            if (chat.imageUrl != null) {
                Glide.with(itemView).load(chat.imageUrl).into(profileimg)
            }

            if(chat.userId != 1){
                if (!chat.skinProblems.isNullOrEmpty()) {
                    for (i in 0 until chat.skinProblems.size) {
                        type = when (chat.skinProblems[i]) {
                            "PIMPLE" -> "여드름"
                            "SENSITIVITY" -> "민감성 피부"
                            "SHADE_SPOT" -> "색조반점"
                            "DARK_CIRCLE" -> "다크서클"
                            "PORE" -> "모공"
                            "BLACK_HEAD" -> "블랙헤드"
                            "WRINKLE" -> "주름"
                            else -> {
                                ""
                            }
                        }
                        if(type != ""){
                            skin += "$type | "
                        }
                    }
                }
                val birthSplit = chat.birthDate?.split("-")

                when(2021- birthSplit?.get(0)?.toInt()!! +1) {
                    in 10..19 -> age = "10대 | "
                    in 20..29 -> age = "20대 | "
                    in 30..39 -> age = "30대 | "
                    in 40..49 -> age = "40대 | "
                    in 50..59 -> age = "50대 | "
                    in 60..69 -> age = "60대 | "
                }

                if (chat.recommendProductCode != null){
                    val skinSplit = (age + skin + chat.recommendProductCode).split("A")
                    if (skinSplit.count() > 1){
                        userinfotext.text = skinSplit[0] + skinSplit[1].removeRange(0, 1)
                    } else {
                        userinfotext.text = skinSplit[0]
                    }
                } else {
                    userinfotext.text = age.replace("|","")
                }
            } else {
                userinfotext.visibility = View.GONE
            }

            registerdate.text = split?.get(0)
            contenttext.text = chat.contents
        }
    }
}