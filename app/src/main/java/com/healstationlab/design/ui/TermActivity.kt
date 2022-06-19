@file:Suppress("NAME_SHADOWING")

package com.healstationlab.design.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayout
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityTermBinding
import com.healstationlab.design.fragment.InfoFragment
import com.healstationlab.design.fragment.UseFragment

class TermActivity : AppCompatActivity() {

    val allFragment = InfoFragment()
    val recommendFragment = UseFragment()

    lateinit var binding : ActivityTermBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageView68.setOnClickListener {
            finish()
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.frame, allFragment)
        transaction.commit()

        binding.tabLayout2.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction = supportFragmentManager.beginTransaction()

                when(tab!!.position){
                    0 -> {transaction.replace(R.id.frame, allFragment).commit()}
                    1 -> {transaction.replace(R.id.frame, recommendFragment).commit()}
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
    }
}