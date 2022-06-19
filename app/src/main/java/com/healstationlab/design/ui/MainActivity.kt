package com.healstationlab.design.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityMyPageBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.reportMeitu
import com.healstationlab.design.fragment.*
import com.healstationlab.design.fragment_nesting.RecommendFragment
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.login.NoDataActivity
import com.healstationlab.design.ui.login.SelectLoginActivity
import com.healstationlab.design.ui.report.ReportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    // View binding
    lateinit var binding : ActivityMyPageBinding
//    private var backPressTime = 0L

    // Fragments
    val homeFragment = HomeFragment()
    val shoppingFragment = ShoppingFragment()
    private val eventFragment = EventFragment()
    private val chatFragment = ChatFragment()
    private val searchFragment = SearchFragment()
    private val mypageFragment = MyPageFragment()
    private val recommendFragment = RecommendFragment()
    //add adi
    var statusCheck : Boolean = true
    var idNoData : Int = 0

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val intent = Intent()

        //add by adi
        getMeitu()
        statusCheck = intent.getBooleanExtra("status-check",true)
        idNoData = intent.getIntExtra("no-data-after-survey",1)
//        when(App.prefs.getBooleanData(Constant.autoLogin)){
//            true -> {
//                Log.d("dia-true", "onCreate: true")
//                Toast.makeText(this, "TRUE", Toast.LENGTH_SHORT).show()
//                binding.constraintMain.visibility = View.VISIBLE
//                }
//            false -> {
//                val intent = Intent(this, ReportActivity::class.java)
//                startActivity(intent)
//                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//                finish()
//            }
//        }

        /** 출석체크 **/
        binding.attandanceButton.setOnClickListener {
            val intent = Intent(this, AttendanceActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 로고 클릭 **/
        binding.homeBtn.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val transaction : FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame, homeFragment).commit()
        }

        /** 마이페이지 **/
        binding.myPage.setOnClickListener {
            val fragmentManager = supportFragmentManager
            val transaction : FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame, mypageFragment).commit()
        }

        /** 환경설정 **/
        binding.leftmenuButton.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 바텀 네비게이션 뷰 **/
        binding.bottomNavigationView.itemIconTintList = null
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            val transaction : FragmentTransaction = fragmentManager.beginTransaction()
            when(it.itemId){
                R.id.home -> {
                    binding.bottomNavigationView.menu.getItem(1).setIcon(R.drawable.shopping)
                    binding.bottomNavigationView.menu.getItem(2).setIcon(R.drawable.event)
                    binding.bottomNavigationView.menu.getItem(3).setIcon(R.drawable.chat)
                    it.setIcon(R.drawable.home_new)
                    transaction.replace(R.id.frame, homeFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.shopping -> {
                    binding.bottomNavigationView.menu.getItem(0).setIcon(R.drawable.home)
                    binding.bottomNavigationView.menu.getItem(2).setIcon(R.drawable.event)
                    binding.bottomNavigationView.menu.getItem(3).setIcon(R.drawable.chat)
                    it.setIcon(R.drawable.shopping_new)
                    transaction.replace(R.id.frame, shoppingFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.event -> {
                    binding.bottomNavigationView.menu.getItem(0).setIcon(R.drawable.home)
                    binding.bottomNavigationView.menu.getItem(1).setIcon(R.drawable.shopping)
                    binding.bottomNavigationView.menu.getItem(3).setIcon(R.drawable.chat)
                    it.setIcon(R.drawable.event_new)
                    transaction.replace(R.id.frame, eventFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.chat -> {
                    binding.bottomNavigationView.menu.getItem(0).setIcon(R.drawable.home)
                    binding.bottomNavigationView.menu.getItem(1).setIcon(R.drawable.shopping)
                    binding.bottomNavigationView.menu.getItem(2).setIcon(R.drawable.event)
                    it.setIcon(R.drawable.chatting_new)
                    transaction.replace(R.id.frame, chatFragment).commit()
                    return@setOnNavigationItemSelectedListener true
                }
            }
            true
        }


        if(Constant.CEHCK){
//            val fragmentManager = supportFragmentManager
            val transaction : FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame, mypageFragment).commit()

        }

        if(Constant.meituCheck){
            getMeitu()
        } else {
            Constant.meituCheck = true
        }
    }

    // Fragment Change
    fun fragmentChange(title : String){
        when(title){
            "go_search" -> fragmentManager.beginTransaction().replace(R.id.frame, searchFragment).commit()
            "go_chat" -> { fragmentManager.beginTransaction().replace(R.id.frame, chatFragment).commit() }
            "go_all" -> {
                fragmentManager.beginTransaction().replace(R.id.frame, recommendFragment).commit()
                binding.bottomNavigationView.selectedItemId = R.id.shopping
            }
            "go_mypage" -> {
                fragmentManager.beginTransaction().replace(R.id.frame, mypageFragment).commit()
            }
        }
    }

    fun setFragment(bundle : Bundle, fragment : Fragment){
        val frag = supportFragmentManager.beginTransaction()
        fragment.arguments = bundle
        frag.replace(R.id.frame, fragment).commit()
    }

    private fun getMeitu(){
        RetrofitMansae.server.getMansaeMeituReport()
            .enqueue(object : Callback<reportMeitu> {
                override fun onFailure(call: Call<reportMeitu>, t: Throwable) {

                }

                override fun onResponse(call: Call<reportMeitu>, response: Response<reportMeitu>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            if(response.body()?.data == null){
                                Log.d("data-null", "onResponse: null data ")
                                binding.bottomNavigationView.selectedItemId = R.id.shopping
                                binding.bottomNavigationView.menu.getItem(0).setIcon(R.drawable.home)
                                binding.bottomNavigationView.menu.getItem(2).setIcon(R.drawable.event)
                                binding.bottomNavigationView.menu.getItem(3).setIcon(R.drawable.chat)
                                binding.bottomNavigationView.menu.getItem(1).setIcon(R.drawable.shopping_new) // 요거 핸들링

                                val fragmentManager = supportFragmentManager
                                val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.frame, shoppingFragment).commit()

                                val intent = Intent(this@MainActivity, NoDataActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)

                            } else {
                                binding.bottomNavigationView.selectedItemId = R.id.home
                                binding.bottomNavigationView.menu.getItem(1).setIcon(R.drawable.shopping)
                                binding.bottomNavigationView.menu.getItem(2).setIcon(R.drawable.event)
                                binding.bottomNavigationView.menu.getItem(3).setIcon(R.drawable.chat)
                                binding.bottomNavigationView.menu.getItem(0).setIcon(R.drawable.home_new) // 요거 핸들링

                                val fragmentManager = supportFragmentManager
                                val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.frame, homeFragment).commit()

                                App.prefs.putBooleanData(Constant.MEITU_DATA_CHECK, true)
                                // App.prefs.putStringData(Constant.STORE_NAME, response.body()!!.data[0].store_name.toString())
                            }
                        }
                    }
                }
            })
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {
                val alertDialog = AlertDialog.Builder(this)
                        .setTitle("beauket")
                        .setMessage("앱을 종료하시겠습니까?")
                        .setPositiveButton("종료"){ _: DialogInterface, _: Int ->
                            moveTaskToBack(true)
                            finishAndRemoveTask()
                            android.os.Process.killProcess(android.os.Process.myPid())
                        }
                        .setNegativeButton("취소"){ _: DialogInterface, _: Int ->
                        }.create()
                alertDialog.show()
            }
        }
        return true
    }

    override fun onPause() {
        super.onPause()
        Constant.meituCheck = true
    }
}
