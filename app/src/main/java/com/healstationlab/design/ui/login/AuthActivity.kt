package com.healstationlab.design.ui.login

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.healstationlab.design.R
import com.healstationlab.design.alert.AlertActivity
import com.healstationlab.design.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinnerItems = arrayListOf("SKT", "KT", "LG U+")
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItems)

        binding.spinner.apply {
            adapter = spinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {

                }
            }
        }

        binding.confirmButton.setOnClickListener {
            val intent = Intent(this, TermOfServiceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.closeButton.setOnClickListener { onBackPressed() }

        binding.authButton.setOnClickListener {
            val intent = Intent(this@AuthActivity, AlertActivity::class.java)
            startActivity(intent)
            binding.authRequestTextView.text = "재요청"
            binding.confirmButton.setBackgroundColor(Color.parseColor("#5A2D8B"))
            binding.confirmTextview.setTextColor(Color.parseColor("#ffffff"))
            testTimer()
        }

        binding.allCheckbox.setOnCheckedChangeListener { _, _ ->
            when (binding.allCheckbox.isChecked) {
                true -> isChecked(true)
                false -> isChecked(false)
            }
        }
    }

    private fun isChecked(bool: Boolean) {
        binding.firstCheckbox.isChecked = bool
        binding.secondCheckbox.isChecked = bool
        binding.thirdeCheckbox.isChecked = bool
        binding.fouthCheckbox.isChecked = bool
    }

    private fun testTimer() {
        object : CountDownTimer(18000, 1000) {
            override fun onFinish() {
                binding.timerTextview.text = "시간초과"
            }

            override fun onTick(p0: Long) {
                binding.timerTextview.text = (p0/1000).toString()
            }
        }.start()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

}