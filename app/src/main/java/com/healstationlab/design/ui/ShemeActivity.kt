package com.healstationlab.design.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.healstationlab.design.databinding.ActivityShemeBinding


class ShemeActivity : AppCompatActivity() {
    lateinit var binding : ActivityShemeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uri = intent.data

        if(uri != null){
            val split = uri.toString().split("=")
            val intent = Intent(this, DetailChatActivity::class.java)
            intent.putExtra("id", split[1].toInt())
            startActivity(intent)
            finish()
        }
    }

}