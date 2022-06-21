@file:Suppress("NAME_SHADOWING")

package com.healstationlab.design.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityAttendanceBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.calendar
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.MaterialCalendarView.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AttendanceActivity : AppCompatActivity() {
    lateinit var binding: ActivityAttendanceBinding
    val day : ArrayList<String> = arrayListOf()
    private var dateFormat : SimpleDateFormat? = null
    var dateFormatserver : SimpleDateFormat? = null
//    var realToday = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.calendarView.showOtherDates = SHOW_OUT_OF_RANGE
        getAttendance()
        binding.imageView64.setOnClickListener { onBackPressed() }

        val today  = (System.currentTimeMillis() / 1000).toInt()
        dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        dateFormatserver = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val dateFormat2 = SimpleDateFormat("yyyyMMdd", Locale.KOREA)


        val reat = dateFormat2.format(today.toLong()*1000) // 오늘 202162

        Constant.today = CalendarDay.today().toString().replace("{","").replace("}","").replace("-","").replace("CalendarDay","") // 202162
        Constant.ca_today = reat.toString()

//        val gdgd = CalendarDay.today().toString().replace("{","").replace("}","").replace("-","").replace("CalendarDay","")
//        val date : Date =
//            dateFormat2.parse(CalendarDay.today().toString().replace("{","").replace("}","").replace("-","").replace("CalendarDay","")) as Date
        val test = dateFormat!!.format(today)

        val aa = dateFormat!!.parse(test)

        val aa2 = dateFormatserver!!.format(aa!!).toString()


        binding.calendarView.selectedDate = CalendarDay.today() // 오늘날짜 칠하기
        //binding.calendarView.addDecorators(CurrentDayDecorator(this@AttendanceActivity, CalendarDay.from()

        val split2 = aa2.split("-")
        binding.calendarView.addDecorators(CurrentDayDecorator2(this@AttendanceActivity, CalendarDay.from(split2[0].toInt(), split2[1].toInt(), split2[2].toInt())))
        Log.d("onklik attendance", "onCreate: 0")

        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            if(reat.toString() == addZero(date.toString().replace("{","").replace("}","").replace("CalendarDay",""))){
                Log.d("onklik attendance", "onCreate: 1")
                if(day.size==0){
                    Log.d("onklik attendance", "onCreate: 2")
                    postCheck(test.replace("-",""))
                    val split = aa2.split("-")
                    binding.calendarView.addDecorators(CurrentDayDecorator(this@AttendanceActivity, CalendarDay.from(split[0].toInt(), split[1].toInt(), split[2].toInt())))
                } else {
                    Log.d("onklik attendance", "onCreate: 3")
                    for(i in 0 until day.size){
                        Log.d("onklik attendance", "onCreate: 4")
                        if(test.replace("-","") != day[i].replace("-","")){
                            Log.d("onklik attendance", "onCreate: 5")
                            postCheck(test.replace("-",""))
                            val split = aa2.split("-")
                            binding.calendarView.addDecorators(CurrentDayDecorator(this@AttendanceActivity, CalendarDay.from(split[0].toInt(), split[1].toInt(), split[2].toInt())))
                        }
                    }
                }
            }
        }
        binding.imageView50.setOnClickListener {
            popupAttendance()
        }
    }

    fun popupAttendance(){
        val inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val menuPopup = inflater!!.inflate(R.layout.popup_attendance,null)
        val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT, false)
        popup.isFocusable = true
        popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
        val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
        val buttonSubmit = menuPopup.findViewById<TextView>(R.id.confirm_text)
        imgClose.setOnClickListener { popup.dismiss() }
        buttonSubmit.setOnClickListener { popup.dismiss() }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    fun getAttendance(){
        RetrofitMansae.server.getCalendar()
            .enqueue(object : Callback<calendar>{
                override fun onFailure(call: Call<calendar>, t: Throwable) {

                }

                override fun onResponse(call: Call<calendar>, response: Response<calendar>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            day.clear()
                            binding.textView185.text = sliceAmountNumber(response.body()!!.data.points.toLong())

                            for(i in response.body()!!.data.dates){ day.add(i) }

                            for(i in 0 until day.size){
                                val split = day[i].split("-")
                                binding.calendarView.addDecorators(CurrentDayDecorator(this@AttendanceActivity, CalendarDay.from(split[0].toInt(), split[1].toInt(), split[2].toInt())))
                            }
                        }
                    }
                }
            })
    }

    private fun postCheck(date : String){
        RetrofitMansae.server.postCheck(date = date)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    getAttendance()
                }
            })
    }

    private fun addZero(date : String) : String{
        val split = date.split("-")

        var month = split[1]
        var day = split[2]

        if(month.length == 1){
            month = "0$month"
        }
        if(day.length == 1){
            day = "0$day"
        }
        return split[0]+month+day
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}

class CurrentDayDecorator(contexts : Context, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable: Drawable = ContextCompat.getDrawable(contexts,R.drawable.checkin)!!
//    private val drawable2: Drawable = ContextCompat.getDrawable(contexts,R.drawable.chool)!!
    private var myDay = currentDay

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.TRANSPARENT))    // ##00000000
        view.setSelectionDrawable(drawable)
    }
}

class CurrentDayDecorator2(contexts : Context, currentDay: CalendarDay) : DayViewDecorator {
    private val drawable2: Drawable = ContextCompat.getDrawable(contexts,R.drawable.chool)!!
    private var myDay = currentDay

    override fun shouldDecorate(day: CalendarDay): Boolean {
        return day == myDay
    }

    override fun decorate(view: DayViewFacade) {
        view.addSpan(ForegroundColorSpan(Color.TRANSPARENT))    // ##00000000
        view.setSelectionDrawable(drawable2)
    }

}