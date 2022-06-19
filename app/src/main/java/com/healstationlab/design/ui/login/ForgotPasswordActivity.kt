package com.healstationlab.design.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.healstationlab.design.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityForgotPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.authCodeEdit.isVisible = false

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.authButton.setOnClickListener {
//            if(binding.authButton.text.toString() == "인증") {
//                val intent = Intent(this, NewPasswordActivity::class.java)
//                startActivity(intent)
//                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//                finish()
//            }

            if(binding.phoneNumberEdit.text.toString().length == 11){
                binding.authButton.text = "인증"
                binding.authCodeEdit.isVisible = true
            }
        }
    }
}