package com.healstationlab.design.alert

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.healstationlab.design.databinding.ActivityAlertBinding

class AlertActivity : AppCompatActivity() {
    lateinit var binding: ActivityAlertBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.background.setOnClickListener { onBackPressed() }
        binding.closeButton.setOnClickListener { onBackPressed() }

        // binding.okButton.setOnClickListener { onBackPressed() }
    }
}