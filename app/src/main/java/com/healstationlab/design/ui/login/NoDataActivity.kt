package com.healstationlab.design.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityNoDataBinding

class NoDataActivity : AppCompatActivity() {
    lateinit var binding : ActivityNoDataBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView421.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.fade_out)
        }

        binding.imageView49.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.fade_out)
        }
    }
}