package com.healstationlab.design.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySevenDaysBinding

class SevenDaysActivity : AppCompatActivity() {
    lateinit var binding : ActivitySevenDaysBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySevenDaysBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.textView286.setOnClickListener {
            onBackPressed()
        }

        binding.imageView119.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.fade_out)
    }
}