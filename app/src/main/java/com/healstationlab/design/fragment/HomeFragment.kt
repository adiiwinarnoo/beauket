@file:Suppress("DEPRECATION")

package com.healstationlab.design.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.util.Log
import android.view.*
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.adapter.*
import com.healstationlab.design.dto.banner
import com.healstationlab.design.dto.popup
import com.healstationlab.design.dto.reportMeitu
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.model.Banner
import com.healstationlab.design.model.BannerLocal
import com.healstationlab.design.model.DotModel
import com.healstationlab.design.model.PopUpNewBanner
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.Constant.Companion.CURRENT_SKIN_POSITION
import com.healstationlab.design.resource.ProgressDialog
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.MainActivity
import com.healstationlab.design.ui.MySkinResultListActivity
import com.healstationlab.design.ui.PopupActivity
import com.healstationlab.design.ui.report.ReportActivity
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment() {
    var mainActivity: MainActivity? = null

//    var img1 = ""
//    var img2 = ""

    lateinit var viewpager: ViewPager2
    lateinit var viewpagerNodata: ViewPager2
    lateinit var indicator : CircleIndicator3

    lateinit var imageView62 : ImageView

    var context2 : Context? = null
    lateinit var username : TextView
    lateinit var skinage : TextView
    lateinit var skintype : TextView
    lateinit var skincolor : ImageView
    lateinit var colorcode : TextView
    lateinit var tscore : TextView
    lateinit var cheeckscore : TextView
    lateinit var chinscore : TextView
    lateinit var slsdscore : TextView
    lateinit var pfpzscore : TextView
    lateinit var hssdxscore : TextView
    lateinit var textView64 : TextView
    lateinit var imageView88 : ImageView

    lateinit var tprogress : ProgressBar
    lateinit var cheekprogress : ProgressBar
    lateinit var chinprogress : ProgressBar
    lateinit var slsdprgress : ProgressBar
    lateinit var pfpzprogress : ProgressBar
    lateinit var hssdxprogress : ProgressBar

    lateinit var tzone : ImageView
    lateinit var bol1 : ImageView
    lateinit var bol2 : ImageView
    lateinit var tuk : ImageView

    lateinit var skinAvg : TextView // 피부 점수
    lateinit var textView69 : TextView // 이전
    lateinit var textView71 : TextView // 개선율
    lateinit var appCompatButton2 : Button


    lateinit var simgak : TextView
    lateinit var nabbem : TextView
    lateinit var yangho : TextView

    private lateinit var greendot : ImageView
    private lateinit var darkbluedot : ImageView
    private lateinit var yellowdot : ImageView
    private lateinit var purpledot : ImageView
    private lateinit var bluedot : ImageView
    private lateinit var crimsondot : ImageView
    private lateinit var reddot : ImageView

    lateinit var txt_banner_statiuc : String

    lateinit var dotRecyclerView : RecyclerView
    lateinit var airecommendbutton : TextView
    lateinit var imageView90 : ImageView

    val today : Int = (System.currentTimeMillis() / 1000).toInt()
    var avg = 0

    var simgakList : ArrayList<DotModel> = arrayListOf()
    var nabbemList : ArrayList<DotModel> = arrayListOf()
    var yanghoList : ArrayList<DotModel> = arrayListOf()
    var isiPopup : ArrayList<PopUpNewBanner> = arrayListOf()

    private var skinDotAdapter : SkinDotAdapter? = null

    lateinit var appCompatButton : Button
    lateinit var constraintPage1 : NestedScrollView
    lateinit var constraintPage2 : NestedScrollView
    lateinit var tvPopup : TextView
    lateinit var adapterLocal : BannerLocal2Adapter

    var imgUrl2 = "https://drive.google.com/file/d/1mpSZSF4xeYCi7oI9ADIsTwKv2l_UbUSJ/view?usp=sharing"
    var imgUrl3 = "https://drive.google.com/file/d/18m7fgRYN2NgDp3jCGJHPYjx14Kyiy3_u/view?usp=sharing"
    var posisiType2 = "MIDDLE"
    var posisiType3 = "MIDDLE"

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        imageView88 = view.findViewById(R.id.imageView88)
        imageView90 = view.findViewById(R.id.imageView90)
        appCompatButton = view.findViewById(R.id.appCompatButton)
        viewpager = view!!.findViewById(R.id.viewPager2)
        viewpagerNodata = view!!.findViewById(R.id.viewPager234)
        tvPopup = view!!.findViewById(R.id.tv_desk42)
        indicator = view.findViewById(R.id.indicator)
        imageView62 = view.findViewById(R.id.imageView62)
        dotRecyclerView = view.findViewById(R.id.dotInfoRecyclerview)
        skinAvg = view.findViewById(R.id.textView70)

        /**Banner Local**/

        /**For the Banners**/

        /**For View With Meitu Data*/
        constraintPage1 = view.findViewById(R.id.scrollView)

        /**For View With Meitu No Data*/
        constraintPage2 = view.findViewById(R.id.scrollView2)

        /**Show Popup Map When in New Banner **/
        tvPopup.setOnClickListener {
            popupNewHomeMap()
        }

        /**Isi Popup**/
        isiPopup.add(
            PopUpNewBanner("먼저, 뷰켓을 만나기 전 피부 관리에 대해서 어떤 생각을 하셨나요?",
            "피부에 특별히 신경을 쓰진 않았어요. 그냥 지저분한 게 보이기 싫어서 쿠션으로 열심히 가리고 다녔어요"))
        val popupAdapter = PopupNewBannerAdapter(isiPopup)


        /** 현재 보기 **/
        imageView90.setOnClickListener {
            mainActivity!!.fragmentChange("go_mypage")
        }

        /** AI 추천 버튼 **/
        airecommendbutton = view.findViewById(R.id.ai_recommend_button)
        airecommendbutton.text = "${App.prefs.getStringData(Constant.NAME)}님에게 맞는 추천 상품"

        airecommendbutton.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", 1)
            mainActivity!!.setFragment(bundle, ShoppingFragment())
        }

        /** 측정일 **/
        textView64 = view.findViewById(R.id.textView64)
        textView64.setOnClickListener {
            val intent = Intent(context, MySkinResultListActivity::class.java)
            startActivityForResult(intent, 1007)
            activity!!.overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        /** 검사결과 **/
        appCompatButton.setOnClickListener {
            val intent = Intent(context, MySkinResultListActivity::class.java)
            startActivityForResult(intent, 1007)
            activity!!.overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        /** 트러블 색깔 **/
        darkbluedot = view.findViewById(R.id.dark_blue_dot)
        yellowdot = view.findViewById(R.id.yellow_dot)
        purpledot = view.findViewById(R.id.purple_dot)
        bluedot = view.findViewById(R.id.blue_dot)
        crimsondot = view.findViewById(R.id.crimson_dot)
        reddot = view.findViewById(R.id.red_dot)
        greendot = view.findViewById(R.id.green_dot)

        /** 심각, 나쁨 양호 **/
        simgak = view.findViewById(R.id.simgak)
        simgak.setOnClickListener { buttonClick(simgak) }

        nabbem = view.findViewById(R.id.nabbem)
        nabbem.setOnClickListener { buttonClick(nabbem) }

        yangho = view.findViewById(R.id.yangho)
        yangho.setOnClickListener { buttonClick(yangho) }


        /** 티존, 양볼, 턱 **/
        tzone = view.findViewById(R.id.t_zone)
        bol1 = view.findViewById(R.id.bol1)
        bol2 = view.findViewById(R.id.bol2)
        tuk = view.findViewById(R.id.tuk)

        /** User data **/
        username = view.findViewById(R.id.user_name)
        skinage = view.findViewById(R.id.skin_age)
        skintype = view.findViewById(R.id.skin_type)
        skincolor = view.findViewById(R.id.skin_color)
        colorcode = view.findViewById(R.id.color_code)

        /** Progress bar **/
        tscore = view.findViewById(R.id.t_score)
        cheeckscore = view.findViewById(R.id.cheeck_score)
        chinscore = view.findViewById(R.id.chin_score)
        slsdscore = view.findViewById(R.id.slsd_score)
        pfpzscore = view.findViewById(R.id.pfpz_score)
        hssdxscore = view.findViewById(R.id.hssdx_score)


        tprogress = view.findViewById(R.id.t_progress)
        cheekprogress = view.findViewById(R.id.cheek_progress)
        chinprogress = view.findViewById(R.id.chin_progress)
        slsdprgress = view.findViewById(R.id.slsd_prgress)
        pfpzprogress = view.findViewById(R.id.pfpz_progress)
        hssdxprogress = view.findViewById(R.id.hssdx_progress)

        appCompatButton2 = view.findViewById(R.id.appCompatButton2)
        textView69 = view.findViewById(R.id.textView69)
        textView71 = view.findViewById(R.id.textView71)


        /** 이전데이터 보기 클릭 **/
        appCompatButton2.setOnClickListener {
            mainActivity!!.fragmentChange("go_mypage")
            CURRENT_SKIN_POSITION += 1
        }

        if(!Constant.CEHCK){
            testMansae(CURRENT_SKIN_POSITION)
            getBanner()
        } else {
            Constant.CEHCK = false
        }
        username.text = App.prefs.getStringData(Constant.NAME) // 1@1.com님 피부 분석
        getUser()

        Log.d("ini-test", "ini test1")
        return view
    }

    /** 메이투 검사 보고서(만세) **/
    fun testMansae(reportPosition : Int){

        val dialog= ProgressDialog.progressDialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        RetrofitMansae.server.getMansaeMeituReport()
            .enqueue(object : Callback<reportMeitu>{
                override fun onFailure(call: Call<reportMeitu>, t: Throwable) {
                    Log.d("ini-test", "ini test-onfailure")
                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<reportMeitu>, response: Response<reportMeitu>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            getBanner()
//                            val imgList : ArrayList<BannerLocal>  = ArrayList()
//                            imgList.add(BannerLocal(R.drawable.banner_30_home,10))
//                            imgList.add(BannerLocal(R.drawable.banner_40_home,11))
//                            imgList.add(BannerLocal(R.drawable.banner_50_home,12))
//                            val adapterLocalHome = BannerLocalHomeAdapter(imgList)
//                            viewpager.adapter = adapterLocalHome
                            if(response.body()?.data != null) {
                                Log.d("ini-test", "ini test-success")
                                constraintPage2.visibility = View.GONE
                                constraintPage1.visibility = View.VISIBLE
                                /** 측정일 구하기 **/
                                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA)
                                val date = Date(response.body()!!.data[reportPosition].created_at.toLong()*1000)
                                textView64.text = "측정일 "+ dateFormat.format(date)

                                avg = (response.body()!!.data[0].preview.slsd.score + response.body()!!.data[0].preview.pfpz.score + response.body()!!.data[0].preview.hssdx.score)/3 // 현재 점수
                                skinAvg.text = (avg).toString()+"점" // 현재 점수


                                /** 이전점수 분기 **/
                                // 데이터가 하나인 경우, 선택한데이터가 마지막인경우
                                if(response.body()!!.data.size == 1 || CURRENT_SKIN_POSITION+1 == response.body()!!.data.size){
                                    appCompatButton2.isVisible = false
                                    textView69.text = "-"
                                    textView71.text = "-" // 개선율
                                } else {
                                    appCompatButton2.isVisible = true
                                    val avg2 = (response.body()!!.data[reportPosition+1].preview.slsd.score + response.body()!!.data[reportPosition+1].preview.pfpz.score + response.body()!!.data[reportPosition+1].preview.hssdx.score)/3 // 이전점수
                                    textView69.text = (avg2).toString()+"점" // 현재점수
                                    val percent = ((avg - avg2) % (avg2)) * 1 // 이전점수
                                    textView71.text = "$percent%"
                                }

                                App.prefs.putStringData(Constant.AGE, response.body()!!.data[reportPosition].preview.age.toString()) // 유저 나이
                                App.prefs.putStringData(Constant.SKIN_TYPE, response.body()!!.data[reportPosition].preview.skin_type.toString()) // 유저 스킨 타입
                                skinage.text = response.body()!!.data[reportPosition].preview.skin_age.toString()+"세" // 피부나이

                                skinage.text = when(response.body()!!.data[reportPosition].preview.skin_age) {
                                    in 0..9 -> "10대"
                                    in 10..19 -> "20대"
                                    in 20..29 -> "20대"
                                    in 30..39 -> "30대"
                                    in 40..49 -> "40대"
                                    in 50..59 -> "50대"
                                    in 60..69 -> "60대"
                                    in 70..79 -> "70대"
                                    in 80..89 -> "80대"
                                    else -> "90대 이상"
                                }

                                /** 점찍기 **/
                                /** 모공 **/
                                when (response.body()!!.data[reportPosition].pore.degree) {
                                    2 -> yanghoList.add(DotModel("모공", degree = 2))
                                    3 -> nabbemList.add(DotModel("모공", degree = 3))
                                    4 -> simgakList.add(DotModel("모공", degree = 4))
                                }

                                /** 블랙헤드 **/
                                when (response.body()!!.data[reportPosition].blackhead.degree) {
                                    2 -> yanghoList.add(DotModel("블랙헤드", degree = 2))
                                    3 -> nabbemList.add(DotModel("블랙헤드", degree = 3))
                                    4 -> simgakList.add(DotModel("블랙헤드", degree = 4))
                                }


                                when (response.body()!!.data[reportPosition].wrinkle.degree) {
                                    2 -> yanghoList.add(DotModel("주름", degree = 2))
                                    3 -> nabbemList.add(DotModel("주름", degree = 3))
                                    4 -> simgakList.add(DotModel("주름", degree = 4))
                                }

                                when (response.body()!!.data[reportPosition].speckle.degree) {
                                    2 -> yanghoList.add(DotModel("색조반점", degree = 2))
                                    3 -> nabbemList.add(DotModel("색조반점", degree = 3))
                                    4 -> simgakList.add(DotModel("색조반점", degree = 4))
                                }

                                when (response.body()!!.data[reportPosition].acne.degree) {
                                    2 -> yanghoList.add(DotModel("여드름", degree = 2))
                                    3 -> nabbemList.add(DotModel("여드름", degree = 3))
                                    4 -> simgakList.add(DotModel("여드름", degree = 4))
                                }

                                when (response.body()!!.data[reportPosition].sensitive.degree) {
                                    1 -> yanghoList.add(DotModel("민감성 피부", degree = 1))
                                    2 -> nabbemList.add(DotModel("민감성 피부", degree = 2))
                                    3 -> simgakList.add(DotModel("민감성 피부", degree = 3))
                                }

                                when (response.body()!!.data[reportPosition].black_rim_of_eye.degree) {
                                    2 -> yanghoList.add(DotModel("다크서클", degree = 2))
                                    3 -> nabbemList.add(DotModel("다크서클", degree = 3))
                                    4 -> simgakList.add(DotModel("다크서클", degree = 4))
                                }

                                /** 스킨 타입 **/
                                when(response.body()!!.data[reportPosition].preview.skin_type){
                                    1 -> skintype.text = "건조성 피부"
                                    2 -> skintype.text = "중성 피부"
                                    3 -> skintype.text = "지성 피부"
                                    4 -> skintype.text = "복합성 피부"
                                }

                                /** Skin color & code **/
                                skincolor.setBackgroundColor(Color.parseColor(response.body()!!.data[reportPosition].preview.color))
                                colorcode.text = response.body()!!.data[reportPosition].preview.color_code

                                /** Progress **/
                                colorSkin(response.body()!!.data[reportPosition].preview.t_score.toInt(), tzone)
                                colorSkin(response.body()!!.data[reportPosition].preview.cheek_score.toInt(), bol1)
                                colorSkin(response.body()!!.data[reportPosition].preview.cheek_score.toInt(), bol2)
                                colorSkin(response.body()!!.data[reportPosition].preview.chin_score.toInt(), tuk)

                                setPrgoress(response.body()!!.data[reportPosition].preview.t_score.toInt(), tscore, tprogress) // T존
                                setPrgoress(response.body()!!.data[reportPosition].preview.cheek_score.toInt(), cheeckscore, cheekprogress) // 양쪽볼
                                setPrgoress(response.body()!!.data[reportPosition].preview.chin_score.toInt(), chinscore, chinprogress) // 턱

                                setPrgoress(response.body()!!.data[reportPosition].preview.slsd.score, slsdscore, slsdprgress) // 노화속도
                                setPrgoress(response.body()!!.data[reportPosition].preview.pfpz.score, pfpzscore, pfpzprogress) // 피부장벽
                                setPrgoress(response.body()!!.data[reportPosition].preview.hssdx.score, hssdxscore, hssdxprogress) // 멜라닌


                                /** 점 찍기 **/
                                simgak.text = "집중관심${simgakList.size}"
                                nabbem.text = "관심필요${nabbemList.size}"
                                yangho.text = "양호${yanghoList.size}"

                                skinDotHide()
                                skinDotShow(simgakList)

                                val dotAdapter = SkinDotAdapter(simgakList)
                                dotRecyclerView.apply {
                                    adapter = dotAdapter
                                    layoutManager = LinearLayoutManager(context)
                                }
                            } else {
                                getBanner()
                                constraintPage1.visibility = View.GONE
                                constraintPage2.visibility = View.VISIBLE
                                textView64.isVisible = false // 측정일
                                Log.d("ini-test", "ini test-failed2")
                                appCompatButton.isVisible = false // 측정일 돋보기 버튼
                                imageView90.isVisible = false // 보기 버튼
                                airecommendbutton

                            }
                        }

                        else -> {
                            Toast.makeText(context2, "서버 에러", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                    }
                    Toast.makeText(context2, "피부정보 갱신완료!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()

                    /** 첫 호출 **/
                    if(!Constant.POPUP_CHECK){
                        imageView62.isVisible = true
                        getPopup()
                        Constant.POPUP_CHECK = true

                    }
                }
            })
    }

    private fun getUser(){

        RetrofitMansae.server.getUserInfo()
            .enqueue(object : Callback<userInfo>{
                override fun onFailure(call: Call<userInfo>, t: Throwable) {
                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            if(response.body()?.data?.recommendProductCode == null || response.body()?.data?.recommendProductCode == "") {
                                val intent = Intent(context!!, ReportActivity::class.java)
                                startActivity(intent)
                            } else {
                                App.prefs.putStringData(Constant.PHONE, response.body()?.data!!.contact.toString()) // 핸드폰 번호
                                App.prefs.putStringData(Constant.SKIN_TYPE, response.body()?.data!!.skinType.toString()) // 피부타입
                                App.prefs.putStringData(Constant.BIRTH, response.body()?.data!!.birthDate)
                                if(!Constant.CEHCK || Constant.meituCheck){
                                    testMansae(CURRENT_SKIN_POSITION)
                                } else {
                                    Constant.CEHCK = false
                                }
                                when(response.body()?.data?.gender){
                                    "MAN" -> {
                                        App.prefs.putStringData(Constant.GENDER, "MAN")
                                        imageView88.setBackgroundResource(R.drawable.dot_man)
                                        context?.let { Glide.with(it).load(R.drawable.my_face_male).into(imageView62) }
                                    }
                                    else -> {
                                        App.prefs.putStringData(Constant.GENDER, "WOMAN")
                                        imageView88.setBackgroundResource(R.drawable.total_state)
                                        context?.let { Glide.with(it).load(R.drawable.my_face).into(imageView62) }
                                    }
                                }
                            }
                        }
                        else -> {

                        }
                    }
                }
            })
    }

    /** 배너 **/
    fun getBanner(){
        RetrofitMansae.server.getBanner()
            .enqueue(object : Callback<banner>{
                override fun onFailure(call: Call<banner>, t: Throwable) {

                }

                override fun onResponse(call: Call<banner>, response: Response<banner>) {

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            val imgList : ArrayList<Banner> = arrayListOf()

                            for(i in response.body()!!.data){
                                if(i.positionType == "UPPER"){
                                    imgList.add(
                                            Banner(
                                                    i.imageUrl, i.linkUrl, i.id, i.product
                                            )
                                    )

                                }
                            }

                            val homeAdapter = HomeAdapter(imgList)

                            /////
                            viewpager.adapter = homeAdapter
                            viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                            viewpagerNodata.adapter = homeAdapter
                            viewpagerNodata.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                            indicator.setViewPager(viewpager)
                            indicator.setViewPager(viewpagerNodata)
                            indicator.createIndicators(imgList.size, 0)
                        }
                        else -> {

                    }
                    }
                }
            })
    }

    fun getPopup(){
        RetrofitMansae.server.getPopup()
            .enqueue(object : Callback<popup>{
                override fun onFailure(call: Call<popup>, t: Throwable) {

                }

                override fun onResponse(call: Call<popup>, response: Response<popup>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            if(response.body()?.data?.size != 0){
                                val intent = Intent(context, PopupActivity::class.java)

                                for(i in 0 until response.body()?.data!!.size){
                                    intent.putExtra("pos", i)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
                                }
                            }
                        }

                        else -> {

                        }
                    }
                }
            })
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setPrgoress(score : Int, score_text : TextView, progressBar : ProgressBar){
        when(score){
            in 0..33 -> {
                score_text.setTextColor(Color.parseColor("#F05E49"))
                score_text.text = score.toString()
                progressBar.progressDrawable = context2!!.getDrawable(R.drawable.progress_red)
                progressBar.progress = score
            }
            in 34..59 -> {
                score_text.setTextColor(Color.parseColor("#F6AC1D"))
                score_text.text = score.toString()
                progressBar.progressDrawable = context2!!.getDrawable(R.drawable.progress_yellow)
                progressBar.progress = score
            }
            in 60..100 -> {
                score_text.setTextColor(Color.parseColor("#2DBCDE"))
                score_text.text = score.toString()
                progressBar.progressDrawable = context2!!.resources.getDrawable(R.drawable.progress_blue)
                progressBar.progress = score
            }
        }
    }

    fun colorSkin(score : Int, img : ImageView){
        when(score){
            in 0..33 -> {
                img.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F05E49"))
            }
            in 34..59 -> {
                img.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F6AC1D"))
            }
            in 60..100 -> {
                img.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#2DBCDE"))
            }
        }
    }

    private fun buttonClick(text : TextView){

        buttonColor()
        text.apply {
            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#5A2D8B"))
            setTextColor(Color.parseColor("#ffffff"))
        }

        when(text.text.toString()){
            "집중관심${simgakList.size}" -> {
                skinDotHide()
                skinDotAdapter = SkinDotAdapter(simgakList)
                dotRecyclerView.apply {
                    adapter = skinDotAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                skinDotShow(simgakList)
            }

            "관심필요${nabbemList.size}" -> {
                skinDotHide()
                skinDotAdapter = SkinDotAdapter(nabbemList)
                dotRecyclerView.apply {
                    adapter = skinDotAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                skinDotShow(nabbemList)
            }

            "양호${yanghoList.size}" -> {
                skinDotHide()
                skinDotAdapter = SkinDotAdapter(yanghoList)
                dotRecyclerView.apply {
                    adapter = skinDotAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                skinDotShow(yanghoList)
            }
        }
    }

    private fun buttonColor(){
        simgak.apply {
            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
            setTextColor(Color.parseColor("#777777"))
        }
        yangho.apply {
            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
            setTextColor(Color.parseColor("#777777"))
        }
        nabbem.apply {
            backgroundTintList = ColorStateList.valueOf(Color.parseColor("#F2F5F5"))
            setTextColor(Color.parseColor("#777777"))
        }
    }

    fun skinDotHide(){
        darkbluedot.isInvisible = true
        yellowdot.isInvisible = true
        purpledot.isInvisible = true
        bluedot.isInvisible = true
        crimsondot.isInvisible = true
        reddot.isInvisible = true
        greendot.isInvisible = true
    }

    fun skinDotShow(list : ArrayList<DotModel>){
        for(i in 0 until list.size){
            when(list[i].result){
                "모공" -> {
                    greendot.isInvisible = false
                }

                "블랙헤드" -> {
                    reddot.isInvisible = false
                }

                "주름" -> {
                    darkbluedot.isInvisible = false
                }

                "색조반점" -> {
                    purpledot.isInvisible = false
                }

                "여드름" -> {
                    yellowdot.isInvisible = false
                }

                "민감성 피부" -> {
                    crimsondot.isInvisible = false
                }

                "다크서클" -> {
                    bluedot.isInvisible = false
                }
            }
        }
    }

    fun popupNewHomeMap(){
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val menuPopup = inflater!!.inflate(R.layout.new_popup_banner_home,null)
        val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT, false)
        popup.isFocusable = true
        popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
        val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
        val buttonSubmit = menuPopup.findViewById<TextView>(R.id.confirm_text)
        imgClose.setOnClickListener { popup.dismiss() }
        buttonSubmit.setOnClickListener { popup.dismiss() }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            when (requestCode) {
                1007 -> {
                    simgakList.clear()
                    yanghoList.clear()
                    nabbemList.clear()
                    CURRENT_SKIN_POSITION = data!!.getIntExtra("position", 0)
                    testMansae(CURRENT_SKIN_POSITION)
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
        this.context2 = context.applicationContext
    }

    override fun onDetach() {
        super.onDetach()
        simgakList.clear()
        yanghoList.clear()
        nabbemList.clear()
        mainActivity = null
    }
}