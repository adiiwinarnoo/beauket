package com.healstationlab.design.ui

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CategoryAdapter
import com.healstationlab.design.databinding.ActivitySettingBinding
import com.healstationlab.design.dto.CategoryData
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.category
import com.healstationlab.design.ui.login.SelectLoginActivity
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingActivity : AppCompatActivity() {
    lateinit var binding : ActivitySettingBinding
    var mainActivity: MainActivity? = null

    var restingCategoryArrayList = ArrayList<ArrayList<CategoryData>>()
    val categoryArrayList : ArrayList<CategoryData> = arrayListOf()
    var cloneList : ArrayList<CategoryData> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        mainActivity = MainActivity()

//        getUser()
        getCategory()

        /** 장바구니 **/
        binding.view125.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 약관동의 **/
        binding.textView284.setOnClickListener {
            val intent = Intent(this, TermActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.imageView104.setOnClickListener {
            val intent = Intent(this, TermActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 로그아웃 **/
        binding.settingBtn.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                    .setTitle("MANSAE")
                    .setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("확인"){ _: DialogInterface, _: Int ->
                        App.prefs.putStringData(Constant.AUTH, "auth")
                        App.prefs.putStringData(Constant.SKIN_TYPE, "skin_type")
                        App.prefs.putBooleanData(Constant.autoLogin, false)
                        val intent = Intent(this, SelectLoginActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                        startActivity(intent)
                        Toast.makeText(this, "로그아웃 되었습니다.", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("취소"){ _: DialogInterface, _: Int ->
                    }.create()
            alertDialog.show()
        }

        /** 회원탈퇴 **/
        binding.textView364.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
                    .setTitle("뷰켓")
                    .setMessage("회원탈퇴 하시겠습니까?")
                    .setPositiveButton("확인"){ _: DialogInterface, _: Int ->
                        signOut()
                    }
                    .setNegativeButton("취소"){ _: DialogInterface, _: Int ->
                    }.create()
            alertDialog.show()
        }

        /** X버튼 클릭 **/
        binding.imageView100.setOnClickListener {
            onBackPressed()
        }

        /** 마이페이지 **/
        binding.view123.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            Constant.CEHCK = true
            Constant.meituCheck = false
            startActivity(intent)
            onBackPressed()
        }

        /** 주문내역 **/
        binding.view124.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            Constant.ORDER = true
            Constant.CEHCK = true
            Constant.meituCheck = false
            startActivity(intent)
            onBackPressed()
        }

        /** faq & 공지사항 이동 **/
        binding.textView360.setOnClickListener {
            val intent = Intent(this, PublicNoticeActivity::class.java)
            intent.putExtra("public", "public")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 알람 설정 **/
        binding.textView362.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        binding.imageView70.setOnClickListener {
            val intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        binding.textView361.setOnClickListener {
            val intent = Intent(this, PublicNoticeActivity::class.java)
            intent.putExtra("faq", "faq")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

//        /** 알람 설정 **/
//        binding.switch2.setOnCheckedChangeListener { compoundButton, b ->
//            when(b){
//                true -> {putAlarm(b)}
//                false -> {putAlarm(b)}
//            }
//        }
        binding.textView295.text = App.prefs.getStringData(Constant.NAME)

        binding.textView2841.setOnClickListener {
            val intent = Intent(this,ProductUseActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_top)
    }

    /** 카테고리 **/
    private fun getCategory(){
        RetrofitMansae.server.getCategory()
                .enqueue(object : Callback<category> {
                    override fun onFailure(call: Call<category>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<category>, response: Response<category>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                var num = 0
                                for(i in response.body()!!.data){
                                    if(num==8){
                                        cloneList = categoryArrayList.clone() as ArrayList<CategoryData>
                                        restingCategoryArrayList.add(cloneList)
                                        num = 0
                                        categoryArrayList.clear()
                                    } else {
                                        categoryArrayList.add(CategoryData(i.id, i.name, i.iconImageUrl))
                                        num++
                                    }
                                }

                                if(num != 0){
                                    restingCategoryArrayList.add(categoryArrayList)
                                }

                                binding.categoryViewpager2.apply {
                                    this.adapter = CategoryAdapter(restingCategoryArrayList, "1", this@SettingActivity)
                                }

                                binding.indicator.setViewPager(binding.categoryViewpager2)
                                binding.indicator.createIndicators(((restingCategoryArrayList.size)), 0)
                            }
                        }
                    }
                })
    }

    private fun signOut(){
        RetrofitMansae.server.signOut()
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            App.prefs.putStringData(Constant.AUTH, "auth")
                            App.prefs.putBooleanData(Constant.autoLogin, false)
                            val intent = Intent(this@SettingActivity, SelectLoginActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                            startActivity(intent)
                            Toast.makeText(this@SettingActivity, "탈퇴되었습니다.", Toast.LENGTH_SHORT).show()
                        }

                        else -> {
                            Toast.makeText(this@SettingActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }
}