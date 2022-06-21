package com.healstationlab.design.dto

data class  comment (
    val responseCode : String,
    val message : String,
    val data : List<CommentData>,
    val totalCount : Int,
    val totalPages : Int,
    val currentPasge : Int

)

data class CommentData(
    val id : Int,
    val board : Board,
    val user : CommentUser,
    val contents : String,
    val boardReComments : List<BoardReComments>?,
    val updatedTime : String,
    )

data class Board(
    val id : Int,
    val user : CommentUser,
    val title : String,
    val contents : String,
    val top : Boolean,
    val createdDate : String?

)

data class CommentUser(
    val loginId : String,
    val name : String,
    val nickname : String,
    val email : String,
    val contact : String,
    val level : Int,
    val points : Int,
    val gender : String,
    val imageUrl : String?,
    val username : String,

)
data class BoardReComments(
    val id : Int?,
    val content: String?,
    val createdAt : String?,
    val updatedAt : String?,
    val userData : CommentUser
)