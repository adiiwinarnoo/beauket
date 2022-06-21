package com.healstationlab.design.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.dto.BoardReComments
import com.healstationlab.design.model.Comment
import com.healstationlab.design.ui.DetailChatActivity
import org.w3c.dom.Text

class ReplyCommentAdapter(private val commentList : List<BoardReComments>)
    : RecyclerView.Adapter<ReplyCommentAdapter.Holder>() {


    var clickReply = ""
    lateinit var editMessage : EditText
//    lateinit var replyBtn : TextView
    lateinit var replyBtnComment : TextView

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_bottom, parent, false)
        val view2 = LayoutInflater.from(parent.context).inflate(R.layout.activity_detail_chat, parent, false)
        editMessage = view2.findViewById(R.id.comment_edit)
//        replyBtn = view.findViewById<TextView>(R.id.reply)
        Log.d("replyAdapter", "onCreateViewHolder: ${commentList.toString()}")
        replyBtnComment = view.findViewById<TextView>(R.id.reply_comment)

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(commentList[position])
    }

    inner class Holder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val profileimg = itemView.findViewById<ImageView>(R.id.profile_img)
        private val nickname = itemView.findViewById<TextView>(R.id.nick_name)
        private val content = itemView.findViewById<TextView>(R.id.reply_content)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val profileimgComment = itemView.findViewById<ImageView>(R.id.profile_img_comment)
        private val nicknameComment = itemView.findViewById<TextView>(R.id.nick_name_comment)
        private val contentComment = itemView.findViewById<TextView>(R.id.reply_content_comment)
        private val dateComment = itemView.findViewById<TextView>(R.id.date_comment)


        init {
        }
        fun bind(comments: BoardReComments){
            if (comments != null){

                if(comments.userData.imageUrl == "" || comments.userData?.imageUrl == "null"){
                    Glide.with(itemView).load(R.drawable.pro2).into(profileimgComment)
                } else {
                    Glide.with(itemView).load(comments.userData?.imageUrl).into(profileimgComment)
                }
                nicknameComment.text = comments.userData?.nickname
                contentComment.text = comments.content
                dateComment.text = comments.createdAt
                replyBtnComment.isVisible = false


            }else{
                profileimgComment.isVisible = false
                nicknameComment.isVisible = false
                contentComment.isVisible = false
                dateComment.isVisible = false
            }

//            if(comment.imageUrl == "" || comment.imageUrl == "null"){
//                Glide.with(itemView).load(R.drawable.pro2).into(profileimg)
//            } else {
//                Glide.with(itemView).load(comment.imageUrl).into(profileimg)
//            }
//            nickname.text = comment.nick_name
//            content.text = comment.content
//            date.text = comment.date

        }


    }
}