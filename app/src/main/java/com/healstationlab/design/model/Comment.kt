package com.healstationlab.design.model

import com.healstationlab.design.dto.BoardReComments
import com.healstationlab.design.dto.comment

data class Comment (
        val idComment : Int?,
        val profile_img : Int?,
        val nick_name : String?,
        val content : String?,
        val date : String?,
        val imageUrl : String?,
        val boardReComments : List<BoardReComments>?,
//        val createdAt : String? = "",
//        val updatedAt : String? = ""
)