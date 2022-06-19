package com.healstationlab.design.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityMyFaceBinding


class MyFaceActivity : AppCompatActivity() {
    lateinit var binding : ActivityMyFaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMyFaceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        when(val img = intent.getStringExtra("img")) {
            "" -> Toast.makeText(this, "하남점 혹은 매장방문을 할 경우 무료로 진단이 가능합니다!", Toast.LENGTH_SHORT).show()
            else -> Glide.with(this).asBitmap().load(img).override(SIZE_ORIGINAL, SIZE_ORIGINAL).into(binding.zoomImg)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}

