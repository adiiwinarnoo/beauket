package com.healstationlab.design.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.healstationlab.design.R
import com.healstationlab.design.adapter.WelcomeScreenAdapter
import com.healstationlab.design.databinding.ActivityWelcomeScreenBinding
import com.healstationlab.design.model.WelcomeScreen
import com.healstationlab.design.ui.login.SelectLoginActivity
import kotlin.math.log

class WelcomeScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityWelcomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var imageScreen : ArrayList<WelcomeScreen>
        var viewpagerScreen : ViewPager2
        var button : Button
        button = findViewById(R.id.btnLoginScreen)
        viewpagerScreen = ViewPager2(this)
        imageScreen = ArrayList()

        imageScreen.add(WelcomeScreen(R.drawable.we_1,1))
        imageScreen.add(WelcomeScreen(R.drawable.we_2,2))
        imageScreen.add(WelcomeScreen(R.drawable.we_34,3))

        val adapter = WelcomeScreenAdapter(imageScreen)
        binding.viewPagerForWelcome.adapter = adapter
//        viewpagerScreen.adapter = adapter
        viewpagerScreen.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        binding.viewPagerForWelcome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                if (position == 1){
                    button.visibility = View.GONE
                }else if(position ==2){
                    button.visibility = View.VISIBLE
                    button.setOnClickListener {
                        val intent = Intent(this@WelcomeScreenActivity, SelectLoginActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                    }
                }else{
                    button.visibility = View.GONE
                }
                super.onPageSelected(position)
            }
        })

//        val itemPager = viewpagerScreen.currentItem
//        Log.d("disini", itemPager.toString())






    }
}