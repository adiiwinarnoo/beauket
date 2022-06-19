package com.healstationlab.design.ui.report

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityReport6Binding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.getQuestion
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Report6Activity : AppCompatActivity(), View.OnClickListener {
    private lateinit var one: String
    private lateinit var two: String
    private lateinit var three: String
    private lateinit var four: String
    private lateinit var five: String
    var userIdNodata: Int = 0

    var six: HashMap<String, ArrayList<Int>> = HashMap()
    var list: ArrayList<Int> = arrayListOf()

    lateinit var binding: ActivityReport6Binding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReport6Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textView332.text = "${App.prefs.getStringData(Constant.NAME)} 회원님에 대한 정보를 더 주세요"

        one = intent.getStringExtra("1")!!
        two = intent.getStringExtra("2")!!
        three = intent.getStringExtra("3")!!
        four = intent.getStringExtra("4")!!
        five = intent.getStringExtra("5")!!

        //add by adi
        userIdNodata = intent.getIntExtra("no-data-after-survey",1)


        binding.cleansingBtn1.setOnClickListener(this)
        binding.cleansingBtn2.setOnClickListener(this)
        binding.cleansingBtn3.setOnClickListener(this)
        binding.cleansingBtn4.setOnClickListener(this)
        binding.cleansingBtn5.setOnClickListener(this)
        binding.cleansingBtn6.setOnClickListener(this)
        binding.cleansingBtn7.setOnClickListener(this)
        binding.cleansingBtn8.setOnClickListener(this)
        binding.cleansingBtn9.setOnClickListener(this)
        binding.cleansingBtn10.setOnClickListener(this)
        binding.cleansingBtn11.setOnClickListener(this)
        binding.cleansingBtn12.setOnClickListener(this)
        binding.cleansingBtn13.setOnClickListener(this)

        binding.imageView102.setOnClickListener {
            onBackPressed()
        }

        binding.textView406.setOnClickListener {
            postQuestion()
        }


        getMyReport()
    }

    private fun radio(btn: TextView, check: ImageView) {
        btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F8849B"))
        btn.setTextColor(Color.parseColor("#ffffff"))
        check.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
    }

    private fun checkBoxText(chk: TextView, pos: Int, chk_img: ImageView) {
        if (chk.currentTextColor == -8947849) {
            list.add(pos)
            chk.setTextColor(Color.parseColor("#ffffff"))
            chk.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F8849B"))
            chk_img.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ffffff"))
        } else {
            list.remove(pos)
            chk.setTextColor(Color.parseColor("#777777"))
            chk.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
            chk_img.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"))
        }
    }

    fun firstCheck(list6: ArrayList<Int>) {
        if (six["6"]!!.isNotEmpty()) {
            for (i in 0 until list6.size) {
                when (list6[i]) {
                    24 -> {
                        radio(binding.cleansingBtn1, binding.cleasingCheck1)
                    }
                    25 -> {
                        radio(binding.cleansingBtn2, binding.cleasingCheck2)
                    }
                    26 -> {
                        radio(binding.cleansingBtn3, binding.cleasingCheck3)
                    }
                    27 -> {
                        radio(binding.cleansingBtn4, binding.cleasingCheck4)
                    }
                    28 -> {
                        radio(binding.cleansingBtn5, binding.cleasingCheck5)
                    }
                    29 -> {
                        radio(binding.cleansingBtn6, binding.cleasingCheck6)
                    }
                    30 -> {
                        radio(binding.cleansingBtn7, binding.cleasingCheck7)
                    }
                    31 -> {
                        radio(binding.cleansingBtn8, binding.cleasingCheck8)
                    }
                    32 -> {
                        radio(binding.cleansingBtn9, binding.cleasingCheck9)
                    }
                    33 -> {
                        radio(binding.cleansingBtn10, binding.cleasingCheck10)
                    }
                    34 -> {
                        radio(binding.cleansingBtn11, binding.cleasingCheck11)
                    }
                    35 -> {
                        radio(binding.cleansingBtn12, binding.cleasingCheck12)
                    }
                    36 -> {
                        radio(binding.cleansingBtn13, binding.cleasingCheck13)
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.cleansing_btn1 -> {
                checkBoxText(binding.cleansingBtn1, 24, binding.cleasingCheck1)
            }
            R.id.cleansing_btn2 -> {
                checkBoxText(binding.cleansingBtn2, 25, binding.cleasingCheck2)
            }
            R.id.cleansing_btn3 -> {
                checkBoxText(binding.cleansingBtn3, 26, binding.cleasingCheck3)
            }
            R.id.cleansing_btn4 -> {
                checkBoxText(binding.cleansingBtn4, 27, binding.cleasingCheck4)
            }
            R.id.cleansing_btn5 -> {
                checkBoxText(binding.cleansingBtn5, 28, binding.cleasingCheck5)
            }
            R.id.cleansing_btn6 -> {
                checkBoxText(binding.cleansingBtn6, 29, binding.cleasingCheck6)
            }
            R.id.cleansing_btn7 -> {
                checkBoxText(binding.cleansingBtn7, 30, binding.cleasingCheck7)
            }
            R.id.cleansing_btn8 -> {
                checkBoxText(binding.cleansingBtn8, 31, binding.cleasingCheck8)
            }
            R.id.cleansing_btn9 -> {
                checkBoxText(binding.cleansingBtn9, 32, binding.cleasingCheck9)
            }
            R.id.cleansing_btn10 -> {
                checkBoxText(binding.cleansingBtn10, 33, binding.cleasingCheck10)
            }
            R.id.cleansing_btn11 -> {
                checkBoxText(binding.cleansingBtn11, 34, binding.cleasingCheck11)
            }
            R.id.cleansing_btn12 -> {
                checkBoxText(binding.cleansingBtn12, 35, binding.cleasingCheck12)
            }
            R.id.cleansing_btn13 -> {
                checkBoxText(binding.cleansingBtn13, 36, binding.cleasingCheck13)
            }
        }
    }

    private fun getMyReport() {
        RetrofitMansae.server.myQuestion()
                .enqueue(object : Callback<getQuestion> {
                    override fun onFailure(call: Call<getQuestion>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<getQuestion>, response: Response<getQuestion>) {
                        when (response.body()?.responseCode) {
                            "SUCCESS" -> {
                                val answer: ArrayList<Int> = arrayListOf()
                                if (response.body()!!.data.isNotEmpty()) {
                                    for (i in response.body()!!.data[response.body()!!.data.size - 1].answers) {
                                        answer.add(i.option.id)
                                    }

                                    for (i in 5 until answer.size) {
                                        list.add(answer[i])
                                    }
                                    six["6"] = list

                                    firstCheck(list)
                                }
                            }
                        }
                    }
                })
    }

    private fun postQuestion() {
        val eleven: HashMap<String, ArrayList<Int>> = HashMap()
        val twentytwo: HashMap<String, ArrayList<Int>> = HashMap()
        val thirtythree: HashMap<String, ArrayList<Int>> = HashMap()
        val fourtyfour: HashMap<String, ArrayList<Int>> = HashMap()
        val fivetyfive: HashMap<String, ArrayList<Int>> = HashMap()

        eleven["1"] = arrayListOf(one.replace("[", "").replace("]", "").toInt())
        twentytwo["2"] = arrayListOf(two.replace("[", "").replace("]", "").toInt())
        thirtythree["3"] = arrayListOf(three.replace("[", "").replace("]", "").toInt())
        fourtyfour["4"] = arrayListOf(four.replace("[", "").replace("]", "").toInt())
        fivetyfive["5"] = arrayListOf(five.replace("[", "").replace("]", "").toInt())


        val body: ArrayList<HashMap<String, ArrayList<Int>>> = arrayListOf()
        body.add(eleven)
        body.add(twentytwo)
        body.add(thirtythree)
        body.add(fourtyfour)
        body.add(fivetyfive)
        six["6"] = list
        body.add(six)


        RetrofitMansae.server.postQuestion(body = body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        Toast.makeText(this@Report6Activity, "설문을 완료했습니다!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Report6Activity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.putExtra("no-data-after-survey",userIdNodata)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                    }
                })
    }
}