package com.healstationlab.design.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySetting2Binding

class Setting2Activity : AppCompatActivity() {
    lateinit var binding : ActivitySetting2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySetting2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.editProfile.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.imageView101.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}