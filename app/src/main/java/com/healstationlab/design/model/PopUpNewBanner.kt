package com.healstationlab.design.model

class PopUpNewBanner(textQuestion:String?,textAnswer:String?) {
   var textQuestion : String = ""
     var textAnswer : String = ""



    init {
        this.textQuestion = textQuestion!!
        this.textAnswer = textAnswer!!
    }

    fun getQuestion(): String {
        return textQuestion
    }
    fun setQuestion(textQues: String?){
        this.textQuestion
    }

    fun getAnswer(): String {
        return textAnswer
    }
    fun setAnswer(textAnsw: String?){
        this.textAnswer
    }
//    fun getTextQuestion(): String? {
//        return textQuestion.toString()
//    }
//    fun setTextQuestion(nama: String?) {
//        textQuestion = textQuestion
//    }
//    fun getTextAnswer(): String? {
//        return textAnswer.toString()
//    }
//    fun setTextAnswer() {
//        this.textAnswer = textAnswer!!
//    }

    override fun toString(): String {
        return "$textQuestion,$textAnswer"
    }


}