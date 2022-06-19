package com.healstationlab.design.customview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.healstationlab.design.R

class ProgressCustomView(context: Context, attrs: AttributeSet? = null, defStyle: Int=0)
    : ConstraintLayout(context, attrs, defStyle) {

    private lateinit var skinName : TextView
    private lateinit var skinScore : TextView
    lateinit var progressBar : ProgressBar

    init {
        initView()
        getAttrs(attrs!!, defStyle)
    }


    private fun initView(){
        val inflaterService = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(inflaterService) as LayoutInflater
        val view : View = layoutInflater.inflate(R.layout.custom_progress_horizontal, this, false)
        addView(view)

        skinName = findViewById(R.id.skin_name)
        skinScore = findViewById(R.id.skin_score)
        progressBar = findViewById(R.id.t_progress)
    }

//    private fun getAttrs(attrs : AttributeSet){
//        val typedArray : TypedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgress)
//        setTypeArray(typedArray)
//    }

    @SuppressLint("CustomViewStyleable")
    private fun getAttrs(attrs : AttributeSet, defStyle : Int){
        val typedArray : TypedArray = context.obtainStyledAttributes(attrs, R.styleable.HorizontalProgress, defStyle, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray){
        val progressBarResId = typedArray.getResourceId(R.styleable.HorizontalProgress_bg, R.drawable.progress_yellow)
        progressBar.setBackgroundResource(progressBarResId)

        val skinNameResId = typedArray.getString(R.styleable.HorizontalProgress_text)
        skinName.text = skinNameResId

        val textColor =typedArray.getColor(R.styleable.HorizontalProgress_textColor, 0)
        skinName.setTextColor(textColor)

        typedArray.recycle()
    }
}