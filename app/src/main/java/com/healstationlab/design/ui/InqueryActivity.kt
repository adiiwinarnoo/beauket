@file:Suppress("NAME_SHADOWING")

package com.healstationlab.design.ui

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityInqueryBinding
import com.healstationlab.design.fragment_nesting.DefaultInqueryFragment
import com.healstationlab.design.fragment_nesting.InqueryFragment

class InqueryActivity : AppCompatActivity() {
    private val inqueryFragment = InqueryFragment()
    private val defaultInquery = DefaultInqueryFragment()


    lateinit var binding : ActivityInqueryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInqueryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firstState()

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame, inqueryFragment)
        transaction.commit()

        /** 뒤로가기 **/
        binding.imageView108.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }

        /** 문의하기 **/
        binding.textView348.setOnClickListener {
            val intent = Intent(this, PostInqueryActivity::class.java)
            startActivityForResult(intent, 1010)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 상품 **/
        binding.textView305.setOnClickListener {
            binding.textView348.isVisible = false
            binding.textView305.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
            binding.textView305.setTextColor(Color.WHITE)
            binding.textView329.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
            binding.textView329.setTextColor(Color.parseColor("#727272"))

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, inqueryFragment).commit()
        }

        binding.textView329.setOnClickListener {
            binding.textView348.isVisible = true
            binding.textView329.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
            binding.textView329.setTextColor(Color.WHITE)
            binding.textView305.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
            binding.textView305.setTextColor(Color.parseColor("#727272"))

            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame, defaultInquery).commit()
        }
    }

    private fun firstState(){
        binding.textView305.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
        binding.textView305.setTextColor(Color.WHITE)
        binding.textView329.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
        binding.textView329.setTextColor(Color.parseColor("#727272"))
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when(requestCode){
                1010 -> {
//                    val transaction = supportFragmentManager.beginTransaction()
//                    transaction.replace(R.id.frame, inqueryFragment)
                    binding.textView305.callOnClick()
                }
                else -> {

                }
            }
        }
    }
}