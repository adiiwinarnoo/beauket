package com.healstationlab.design.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.model.Comment
import com.healstationlab.design.ui.DetailChatActivity
import org.w3c.dom.Text

class CommentAdapter(private val commentList : ArrayList<Comment>,private val itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<CommentAdapter.Holder>() {

    lateinit var editMessage : EditText
    lateinit var replyBtn : TextView

    interface ItemClickListener {
        fun onClick(position: Int)
    }

//    fun setItemClickListener(itemClickListener: ItemClickListener){
//        this.itemClickListener = itemClickListener
//    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        val view2 = LayoutInflater.from(parent.context).inflate(R.layout.activity_detail_chat, parent, false)
        editMessage = view2.findViewById(R.id.comment_edit)
        replyBtn = view.findViewById<TextView>(R.id.reply)

        return Holder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(commentList[position])
    }

    inner class Holder(itemView : View,itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView){
        private val profileimg = itemView.findViewById<ImageView>(R.id.profile_img)
        private val nickname = itemView.findViewById<TextView>(R.id.nick_name)
        private val content = itemView.findViewById<TextView>(R.id.reply_content)
        private val date = itemView.findViewById<TextView>(R.id.date)
        init {
            replyBtn.setOnClickListener {
                itemClickListener.onClick(adapterPosition)
            }
        }
        fun bind(comment : Comment){
            if(comment.imageUrl == "" || comment.imageUrl == "null"){
                Glide.with(itemView).load(R.drawable.pro2).into(profileimg)
            } else {
                Glide.with(itemView).load(comment.imageUrl).into(profileimg)
            }
            nickname.text = comment.nick_name
            content.text = comment.content
            date.text = comment.date
        }

    }
}