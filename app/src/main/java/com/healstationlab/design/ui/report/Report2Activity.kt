package com.healstationlab.design.ui.report

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityReport2Binding
import com.healstationlab.design.dto.getQuestion
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Report2Activity : AppCompatActivity(), View.OnClickListener {

    var two : HashMap<String, ArrayList<Int>> = HashMap()
    var three : HashMap<String, ArrayList<Int>> = HashMap()
    var four : HashMap<String, ArrayList<Int>> = HashMap()
    var five : HashMap<String, ArrayList<Int>> = HashMap()
    var six : HashMap<String, ArrayList<Int>> = HashMap()
    var list : ArrayList<Int> = arrayListOf()
    private lateinit var one : String


    lateinit var binding : ActivityReport2Binding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityReport2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.textView332.text = "${App.prefs.getStringData(Constant.NAME)} 회원님에 대한 정보를 더 주세요"
        getMyReport()

        one = intent.getStringExtra("1")!!

        binding.imageView102.setOnClickListener {
            onBackPressed()
        }

        binding.textView406.setOnClickListener {
//            postQuestion()
            if(two.isNotEmpty()){
                val intent = Intent(this, Report3Activity::class.java)
                intent.putExtra("1", one)
                intent.putExtra("2", two["2"].toString())
                startActivity(intent)
                overridePendingTransition(R.xml.slide_right_to_left, R.xml.no_chagne) //edit by adi
            } else {
                Toast.makeText(this, "항목을 선택해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        /** 클렌징(세안)후 당신의 얼굴 피부 상태는 어떤가요? **/
        binding.cleansingBtn1.setOnClickListener(this)
        binding.cleansingBtn2.setOnClickListener(this)
        binding.cleansingBtn3.setOnClickListener(this)
        binding.cleansingBtn4.setOnClickListener(this)
        binding.cleansingBtn5.setOnClickListener(this)

        /** 화장품을 사용한 뒤 피부 트러블, 발진, 가려움 혹은 따끔거리는 증상을 경험한 적이 있나요? **/
        binding.troubleBtn1.setOnClickListener(this)
        binding.troubleBtn2.setOnClickListener(this)
        binding.troubleBtn3.setOnClickListener(this)
        binding.troubleBtn4.setOnClickListener(this)
        binding.troubleBtn5.setOnClickListener(this)

        /** 햇볕에 나가면, 주근깨가 생기는 편인가요? **/
        binding.sunBtn1.setOnClickListener(this)
        binding.sunBtn2.setOnClickListener(this)
        binding.sunBtn3.setOnClickListener(this)
        binding.sunBtn4.setOnClickListener(this)
        binding.sunBtn5.setOnClickListener(this)

        /** 얼굴에 붉은 여드름이 발생한 경험이 있나요? **/
        binding.yuBtn1.setOnClickListener(this)
        binding.yuBtn2.setOnClickListener(this)
        binding.yuBtn3.setOnClickListener(this)
        binding.yuBtn4.setOnClickListener(this)

        /** 얼굴에 주름이 있나요? **/
        binding.jooBtn1.setOnClickListener(this)
        binding.jooBtn2.setOnClickListener(this)
        binding.jooBtn3.setOnClickListener(this)
        binding.jooBtn4.setOnClickListener(this)

        /** 사용하는 화장품은 무엇인가요? **/
        binding.chkBtn1.setOnClickListener(this)
        binding.chkBtn2.setOnClickListener(this)
        binding.chkBtn3.setOnClickListener(this)
        binding.chkBtn4.setOnClickListener(this)
        binding.chkBtn5.setOnClickListener(this)
        binding.chkBtn6.setOnClickListener(this)
        binding.chkBtn7.setOnClickListener(this)
        binding.chkBtn8.setOnClickListener(this)
        binding.chkBtn9.setOnClickListener(this)
        binding.chkBtn10.setOnClickListener(this)
        binding.chkBtn11.setOnClickListener(this)
        binding.chkBtn12.setOnClickListener(this)
        binding.chkBtn13.setOnClickListener(this)
    }

    fun firstCheck() {
        if(two["2"]!!.isNotEmpty()){
            when(two["2"]){
                arrayListOf(6) -> { radio(binding.cleansingBtn1, binding.cleasingCheck1)}
                arrayListOf(7) -> {radio(binding.cleansingBtn2, binding.cleasingCheck2)}
                arrayListOf(8) -> {radio(binding.cleansingBtn3, binding.cleasingCheck3)}
                arrayListOf(9) -> {radio(binding.cleansingBtn4, binding.cleasingCheck4)}
                arrayListOf(10) -> {radio(binding.cleansingBtn5, binding.cleasingCheck5)}
            }

//            for(i in 0..list6.size-1){
//                when(list6[i]){
//                    24 -> {radio(binding.chkBtn1)}
//                    25 -> {radio(binding.chkBtn2)}
//                    26 -> {radio(binding.chkBtn3)}
//                    27 -> {radio(binding.chkBtn4)}
//                    28 -> {radio(binding.chkBtn5)}
//                    29 -> {radio(binding.chkBtn6)}
//                    30 -> {radio(binding.chkBtn7)}
//                    31 -> {radio(binding.chkBtn8)}
//                    32 -> {radio(binding.chkBtn9)}
//                    33 -> {radio(binding.chkBtn10)}
//                    34 -> {radio(binding.chkBtn11)}
//                    35 -> {radio(binding.chkBtn12)}
//                    36 -> {radio(binding.chkBtn13)}
//                }
//            }
        }
    }

    private fun radio(btn : TextView, check : ImageView){
        btn.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F8849B"))
        btn.setTextColor(Color.parseColor("#ffffff"))
        check.backgroundTintList = ColorStateList.valueOf(Color.WHITE)
    }

    private fun radio1VisibleFalse(){
        binding.cleansingBtn1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
        binding.cleansingBtn2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
        binding.cleansingBtn3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
        binding.cleansingBtn4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
        binding.cleansingBtn5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))

        binding.cleansingBtn1.setTextColor(Color.parseColor("#777777"))
        binding.cleansingBtn2.setTextColor(Color.parseColor("#777777"))
        binding.cleansingBtn3.setTextColor(Color.parseColor("#777777"))
        binding.cleansingBtn4.setTextColor(Color.parseColor("#777777"))
        binding.cleansingBtn5.setTextColor(Color.parseColor("#777777"))

        binding.cleasingCheck1.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"))
        binding.cleasingCheck2.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"))
        binding.cleasingCheck3.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"))
        binding.cleasingCheck4.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"))
        binding.cleasingCheck5.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#CCCCCC"))
    }

//    fun radio2VisibleFalse(){
//        binding.troubleBtn1.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.troubleBtn2.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.troubleBtn3.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.troubleBtn4.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.troubleBtn5.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.troubleBtn1.setTextColor(Color.parseColor("#777777"))
//        binding.troubleBtn2.setTextColor(Color.parseColor("#777777"))
//        binding.troubleBtn3.setTextColor(Color.parseColor("#777777"))
//        binding.troubleBtn4.setTextColor(Color.parseColor("#777777"))
//        binding.troubleBtn5.setTextColor(Color.parseColor("#777777"))
//    }

//    fun radio3VisibleFalse(){
//        binding.sunBtn1.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.sunBtn2.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.sunBtn3.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.sunBtn4.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.sunBtn5.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.sunBtn1.setTextColor(Color.parseColor("#777777"))
//        binding.sunBtn2.setTextColor(Color.parseColor("#777777"))
//        binding.sunBtn3.setTextColor(Color.parseColor("#777777"))
//        binding.sunBtn4.setTextColor(Color.parseColor("#777777"))
//        binding.sunBtn5.setTextColor(Color.parseColor("#777777"))
//    }

//    fun radio4VisibleFalse(){
//        binding.yuBtn1.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.yuBtn2.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.yuBtn3.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.yuBtn4.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.yuBtn1.setTextColor(Color.parseColor("#777777"))
//        binding.yuBtn2.setTextColor(Color.parseColor("#777777"))
//        binding.yuBtn3.setTextColor(Color.parseColor("#777777"))
//        binding.yuBtn4.setTextColor(Color.parseColor("#777777"))
//    }

//    fun radio5VisibleFalse(){
//        binding.jooBtn1.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.jooBtn2.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.jooBtn3.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.jooBtn4.setBackgroundResource(R.drawable.detail_backgorund)
//        binding.jooBtn1.setTextColor(Color.parseColor("#777777"))
//        binding.jooBtn2.setTextColor(Color.parseColor("#777777"))
//        binding.jooBtn3.setTextColor(Color.parseColor("#777777"))
//        binding.jooBtn4.setTextColor(Color.parseColor("#777777"))
//    }

    private fun checkBoxText(chk : Button, pos : Int){
        if(chk.currentTextColor == -8947849){
            list.add(pos)
            chk.setTextColor(Color.parseColor("#ffffff"))
            chk.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F8849B"))
        } else {
            list.remove(pos)
            chk.setTextColor(Color.parseColor("#777777"))
            chk.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
        }
    }



    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    override fun onClick(view: View?) {
        when(view!!.id){
            R.id.cleansing_btn1 -> {
                two.clear()
                radio1VisibleFalse()
                radio(binding.cleansingBtn1, binding.cleasingCheck1)
                two["2"] = arrayListOf(6)
            }
            R.id.cleansing_btn2 -> {
                two.clear()
                radio1VisibleFalse()
                radio(binding.cleansingBtn2, binding.cleasingCheck2)
                two["2"] = arrayListOf(7)
            }
            R.id.cleansing_btn3 -> {
                two.clear()
                radio1VisibleFalse()
                radio(binding.cleansingBtn3, binding.cleasingCheck3)
                two["2"] = arrayListOf(8)
            }
            R.id.cleansing_btn4 -> {
                two.clear()
                radio1VisibleFalse()
                radio(binding.cleansingBtn4, binding.cleasingCheck4)
                two["2"] = arrayListOf(9)
            }
            R.id.cleansing_btn5 -> {
                two.clear()
                radio1VisibleFalse()
                radio(binding.cleansingBtn5, binding.cleasingCheck5)
                two["2"] = arrayListOf(10)
            }

            R.id.chk_btn1 -> {
                checkBoxText(binding.chkBtn1, 24)
            }
            R.id.chk_btn2 -> {
                checkBoxText(binding.chkBtn2, 25)
            }
            R.id.chk_btn3 -> {
                checkBoxText(binding.chkBtn3, 26)
            }
            R.id.chk_btn4 -> {
                checkBoxText(binding.chkBtn4, 27)
            }
            R.id.chk_btn5 -> {
                checkBoxText(binding.chkBtn5, 28)
            }
            R.id.chk_btn6 -> {
                checkBoxText(binding.chkBtn6, 29)
            }
            R.id.chk_btn7 -> {
                checkBoxText(binding.chkBtn7, 30)
            }
            R.id.chk_btn8 -> {
                checkBoxText(binding.chkBtn8, 31)
            }
            R.id.chk_btn9 -> {
                checkBoxText(binding.chkBtn9, 32)
            }
            R.id.chk_btn10 -> {
                checkBoxText(binding.chkBtn10, 33)
            }
            R.id.chk_btn11 -> {
                checkBoxText(binding.chkBtn11, 34)
            }
            R.id.chk_btn12 -> {
                checkBoxText(binding.chkBtn12, 35)
            }
            R.id.chk_btn13 -> {
                checkBoxText(binding.chkBtn13, 36)
            }
        }
    }

//    fun postQuestion(){
//        var body : ArrayList<HashMap<String, ArrayList<Int>>> = arrayListOf()
//        body.add(two)
//        body.add(three)
//        body.add(four)
//        body.add(five)
//        six["6"] = list
//        body.add(six)
//
//        RetrofitMansae.server.postQuestion(body = body)
//                .enqueue(object : Callback<auth>{
//                    override fun onFailure(call: Call<auth>, t: Throwable) {
//
//                    }
//
//                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
//
//                    }
//                })
//    }

    private fun getMyReport(){
        RetrofitMansae.server.myQuestion()
                .enqueue(object : Callback<getQuestion>{
                    override fun onFailure(call: Call<getQuestion>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<getQuestion>, response: Response<getQuestion>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                val answer : ArrayList<Int> = arrayListOf()
                                if(response.body()!!.data.isNotEmpty()){
                                    for(i in response.body()!!.data[response.body()!!.data.size-1].answers){ answer.add(i.option.id) }

                                    two["2"] = arrayListOf(answer[1])
                                    three["3"] = arrayListOf(answer[2])
                                    four["4"] = arrayListOf(answer[3])
                                    five["5"] = arrayListOf(answer[4])

                                    for(i in 5 until answer.size){
                                        list.add(answer[i])
                                    }
                                    six["6"] = list

                                    firstCheck()
                                }
                            }
                        }
                    }
                })
    }
}