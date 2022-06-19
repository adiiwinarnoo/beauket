package com.healstationlab.design.model

class ImageBoard (imgUrl : String) {
    var imgUrl : String = ""

    init {
        this.imgUrl = imgUrl!!
    }

    fun getImage(): String {
        return imgUrl
    }
    fun setImage(image: Int?){
        this.imgUrl
    }
}