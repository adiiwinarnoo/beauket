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
import com.healstationlab.design.dto.CommentData
import com.healstationlab.design.dto.address
import com.healstationlab.design.model.Comment
import com.healstationlab.design.ui.DetailChatActivity
import org.w3c.dom.Text

class CommentAdapter(private val commentList : ArrayList<CommentData>,private var itemClickListener: ItemClickListener)
    : RecyclerView.Adapter<CommentAdapter.Holder>() {


    var clickReply = ""
    lateinit var editMessage : EditText
    lateinit var replyBtn : TextView
    lateinit var replyBtnComment : TextView

    interface ItemClickListener {
        fun onClick(position: Int)
    }

    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comment_item, parent, false)
        val view2 = LayoutInflater.from(parent.context).inflate(R.layout.activity_detail_chat, parent, false)
        editMessage = view2.findViewById(R.id.comment_edit)
        replyBtn = view.findViewById<TextView>(R.id.reply)
//        val recyclerView = view.findViewById<TextView>(R.id.recycleComment)
//        replyBtnComment = view.findViewById<TextView>(R.id.reply_comment)

        return Holder(view,itemClickListener)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(commentList[position])
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(position)
        }

    }

    inner class Holder(itemView : View,itemClickListener: ItemClickListener) : RecyclerView.ViewHolder(itemView){
        private val profileimg = itemView.findViewById<ImageView>(R.id.profile_img)
        private val nickname = itemView.findViewById<TextView>(R.id.nick_name)
        private val content = itemView.findViewById<TextView>(R.id.reply_content)
        private val date = itemView.findViewById<TextView>(R.id.date)
        private val recyclerView = itemView.findViewById<RecyclerView>(R.id.recycleComment)
//        private val profileimgComment = itemView.findViewById<ImageView>(R.id.profile_img_comment)
//        private val nicknameComment = itemView.findViewById<TextView>(R.id.nick_name_comment)
//        private val contentComment = itemView.findViewById<TextView>(R.id.reply_content_comment)
//        private val dateComment = itemView.findViewById<TextView>(R.id.date_comment)


        init {
            replyBtn.setOnClickListener {
                itemClickListener.onClick(commentList[adapterPosition]!!.id)
            }
        }
        fun bind(comment : CommentData){
//            if (commentList[adapterPosition].boardReComments != null){
//                if(comment.imageUrl == "" || comment.imageUrl == "null"){
//                    Glide.with(itemView).load(R.drawable.pro2).into(profileimgComment)
//                } else {
//                    Glide.with(itemView).load(comment.imageUrl).into(profileimgComment)
//                }
//                for (i in comment.boardReComments!!){
//                    nicknameComment.text = i.userData.nickname
//                    contentComment.text = i.content
//                    dateComment.text = i.createdAt
//                }
//                replyBtnComment.isVisible = false
//
//            }


            if(comment.user.imageUrl == "" || comment.user.imageUrl == "null"){
                Glide.with(itemView).load(R.drawable.pro2).into(profileimg)
            } else {
                Glide.with(itemView).load(comment.user.imageUrl).into(profileimg)
            }
            nickname.text = comment.user.nickname
            content.text = comment.contents
            date.text = comment.board.createdDate

            recyclerView.apply {
                adapter = ReplyCommentAdapter(comment.boardReComments!!)
                layoutManager = LinearLayoutManager(context)
            }


        }


    }
}