package com.healstationlab.design.model

class WelcomeScreen (image: Int?, id : Int?) {
    private var image : Int = 0
    private var id : Int = 0

    init {
        this.image = image!!
        this.id = id!!
    }

    fun getImage():Int{
        return image
    }
    fun setImage(image: Int?){
        this.image
    }
    fun getIdImage() : Int{
        return id
    }

    override fun toString(): String {
        return "$image,$id"
    }


}