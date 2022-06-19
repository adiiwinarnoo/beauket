package com.healstationlab.design.fragment_nesting

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.adapter.SkinDetail2Adapter
import com.healstationlab.design.adapter.SkinDetailAdapter
import com.healstationlab.design.adapter.SkinDotAdapter
import com.healstationlab.design.dto.*
import com.healstationlab.design.fragment.ShoppingFragment
import com.healstationlab.design.model.DotModel
import com.healstationlab.design.resource.*
import com.healstationlab.design.resource.Constant.Companion.CURRENT_SKIN_POSITION
import com.healstationlab.design.ui.MainActivity
import com.healstationlab.design.ui.MyFaceActivity
import com.healstationlab.design.ui.MySkinResultListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SkinFragment : Fragment() {
    var mainActivity: MainActivity? = null

    var sensitive = 0
    var speckle = 0
    var wrinkle = 0
    var acne = 0
    var blackhead = 0
    var blackRimOfEye = 0
    var pore = 0

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
    private lateinit var imageView89 : ImageView
    private lateinit var imageView91 : ImageView

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

    var img1 = ""
    var img2 = ""

    lateinit var textView85 : TextView

    lateinit var imageView79 : ImageView
    lateinit var imageView96 : ImageView
    lateinit var appCompatButton4 : Button


    lateinit var skinrecyclerview : RecyclerView
    lateinit var skin2recyclerview : RecyclerView

    var skinDetailArrayList = arrayListOf<Any>()
    var skinDetail2ArrayList = arrayListOf<Any>()

    var simgakList : ArrayList<DotModel> = arrayListOf()
    var nabbemList : ArrayList<DotModel> = arrayListOf()
    var yanghoList : ArrayList<DotModel> = arrayListOf()

    lateinit var dotRecyclerView : RecyclerView

    private var skinDotAdapter : SkinDotAdapter? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_skin, container, false)
        imageView91 = view.findViewById(R.id.imageView91)
        imageView89 = view.findViewById(R.id.imageView89)
        username = view.findViewById(R.id.user_name)
        skinage = view.findViewById(R.id.skin_age)
        skintype = view.findViewById(R.id.skin_type)
        skincolor = view.findViewById(R.id.skin_color)
        colorcode = view.findViewById(R.id.color_code)

        tzone = view.findViewById(R.id.t_zone)
        bol1 = view.findViewById(R.id.bol1)
        bol2 = view.findViewById(R.id.bol2)
        tuk = view.findViewById(R.id.tuk)

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

        textView85 = view.findViewById(R.id.textView85)

        skinrecyclerview = view.findViewById(R.id.skin_detail_recyclerview)
        skin2recyclerview = view.findViewById(R.id.skin_detail2_recyclerview)
        dotRecyclerView = view.findViewById(R.id.dotInfoRecyclerview)


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

        imageView79 = view.findViewById(R.id.imageView79) // 왼쪽
        imageView96 = view.findViewById(R.id.imageView96) // 오른쪽
        appCompatButton4 = view.findViewById(R.id.appCompatButton4)
        val appCompatButton7 = view.findViewById<TextView>(R.id.appCompatButton7)
        appCompatButton7.text = App.prefs.getStringData(Constant.NAME)+"님에게 맞는 추천 상품"

        appCompatButton7.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", 1)
            mainActivity!!.setFragment(bundle, ShoppingFragment())
        }

        textView85.setOnClickListener {
            val intent = Intent(context, MySkinResultListActivity::class.java)
            startActivityForResult(intent, 1007)
            activity!!.overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        /** 실 서버 연결후 사용자 얼글 img 보내기 (glide 사용)**/
        imageView79.setOnClickListener {
            val intnet = Intent(context, MyFaceActivity::class.java)
            intnet.putExtra("img", img1)
            startActivity(intnet)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        imageView96.setOnClickListener {
            val intnet = Intent(context, MyFaceActivity::class.java)
            intnet.putExtra("img", img2)
            startActivity(intnet)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        val bundle = arguments
        val id = bundle?.getInt("position")

        if(id != null){
            testMansae(bundle.getInt("position"))

        } else {
            testMansae(CURRENT_SKIN_POSITION)

        }

        appCompatButton4.setOnClickListener {
            val intent = Intent(context, MySkinResultListActivity::class.java)
            startActivityForResult(intent, 1007)
            activity!!.overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
        }

        when(App.prefs.getStringData(Constant.GENDER)){
            "MAN" -> {
                imageView91.setBackgroundResource(R.drawable.dot_man)
                Glide.with(context!!).load(R.drawable.my_face_male).into(imageView89)
            }
            else -> {
                imageView91.setBackgroundResource(R.drawable.total_state)
                Glide.with(context!!).load(R.drawable.my_face).into(imageView89)
            }
        }

        return view
    }

    /** 메이투 검사 보고서(만세) **/
    private fun testMansae(reportPosition : Int){
        val dialog= ProgressDialog.progressDialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        RetrofitMansae.server.getMansaeMeituReport()
            .enqueue(object : Callback<reportMeitu>{
                override fun onFailure(call: Call<reportMeitu>, t: Throwable) {

                }

                @SuppressLint("ResourceType", "SetTextI18n")
                override fun onResponse(call: Call<reportMeitu>, response: Response<reportMeitu>) {

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            Glide.with(context!!).load(Glide.with(context!!).load(Color.BLACK).into(imageView79))
                            Glide.with(context!!).load(Glide.with(context!!).load(Color.BLACK).into(imageView96))

                            username.text = App.prefs.getStringData(Constant.NAME)

                            if(response.body()?.data != null){
                                getSkinScore(
                                    pore2 = response.body()!!.data[CURRENT_SKIN_POSITION].pore.score,
                                    blackhead2 = response.body()!!.data[CURRENT_SKIN_POSITION].blackhead.score,
                                    blackRimOfEye2 = response.body()!!.data[CURRENT_SKIN_POSITION].black_rim_of_eye.score,
                                    acne2 = response.body()!!.data[CURRENT_SKIN_POSITION].acne.score,
                                    wrinkle2 = response.body()!!.data[CURRENT_SKIN_POSITION].wrinkle.score,
                                    speckle2 = response.body()!!.data[CURRENT_SKIN_POSITION].speckle.score,
                                    sensitive2 = response.body()!!.data[CURRENT_SKIN_POSITION].sensitive.score)


                                val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.KOREA)
                                val date = Date(response.body()!!.data[reportPosition].created_at.toLong()*1000)
                                textView85.text = "측정일 "+ dateFormat.format(date)

                                /** 이전점수 분기 **/
//                            if(response.body()!!.data.size >= 2){
//
//                            }
                                App.prefs.putStringData(Constant.AGE, response.body()!!.data[reportPosition].preview.age.toString()) // 유저 나이
                                App.prefs.putStringData(Constant.SKIN_TYPE, response.body()!!.data[reportPosition].preview.skin_type.toString()) // 유저 스킨 타입

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

                                if(response.body()?.data!![CURRENT_SKIN_POSITION].pic_list?.`0` != null){
                                    when (CURRENT_SKIN_POSITION) {
                                        0 -> {
                                            if(response.body()!!.data.size == 1) {
                                                img1 = response.body()?.data!![CURRENT_SKIN_POSITION].pic_list?.`0`!!.`1`
                                                Glide.with(context!!).load(response.body()?.data!![CURRENT_SKIN_POSITION].pic_list!!.`0`.`1`).into(imageView79)
                                            } else {
                                                img1 = response.body()?.data!![CURRENT_SKIN_POSITION].pic_list?.`0`!!.`1`
                                                img2 = response.body()?.data!![CURRENT_SKIN_POSITION+1].pic_list?.`0`!!.`1`
                                                Glide.with(context!!).load(response.body()?.data!![CURRENT_SKIN_POSITION].pic_list!!.`0`.`1`).into(imageView96)
                                                Glide.with(context!!).load(response.body()?.data!![CURRENT_SKIN_POSITION+1].pic_list!!.`0`.`1`).into(imageView79)
                                            }
                                        }
                                        response.body()!!.data.size-1 -> {
                                            img1 = "#ffffff"
                                            img2 = response.body()?.data!![CURRENT_SKIN_POSITION].pic_list?.`0`?.`1`!!

                                            Glide.with(context!!).load(Glide.with(context!!).load(Color.BLACK).into(imageView79))
                                            Glide.with(context!!).load(response.body()?.data!![CURRENT_SKIN_POSITION].pic_list!!.`0`.`1`).into(imageView96)
                                        }
                                        else -> {
                                            img1 = response.body()?.data!![CURRENT_SKIN_POSITION].pic_list!!.`0`.`1`
                                            img2 = response.body()?.data!![CURRENT_SKIN_POSITION+1].pic_list!!.`0`.`1`

                                            Glide.with(context!!).load(response.body()?.data!![CURRENT_SKIN_POSITION].pic_list!!.`0`.`1`).into(imageView79)
                                            Glide.with(context!!).load(response.body()?.data!![CURRENT_SKIN_POSITION+1].pic_list!!.`0`.`1`).into(imageView96)
                                        }
                                    }
                                }

                                /** 점찍기 **/
                                when (response.body()!!.data[reportPosition].pore.degree) {
                                    2 -> yanghoList.add(DotModel("모공", degree = 2))
                                    3 -> nabbemList.add(DotModel("모공", degree = 3))
                                    4 -> simgakList.add(DotModel("모공", degree = 4))
                                }

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

                                skinDetailArrayList = arrayListOf(
                                        reportMeitu.Data.Preview.Slsd(response.body()!!.data[reportPosition].preview.slsd.score, response.body()!!.data[reportPosition].preview.slsd.level, response.body()!!.data[reportPosition].preview.slsd.sub_item),
                                        reportMeitu.Data.Preview.Pfpz(response.body()!!.data[reportPosition].preview.pfpz.score, response.body()!!.data[reportPosition].preview.pfpz.level, response.body()!!.data[reportPosition].preview.pfpz.sub_item),
                                        reportMeitu.Data.Preview.Hssdx(response.body()!!.data[reportPosition].preview.hssdx.score, response.body()!!.data[reportPosition].preview.hssdx.level, response.body()!!.data[reportPosition].preview.hssdx.sub_item))

                                skinDetail2ArrayList = arrayListOf(
                                        reportMeitu.Data.Pore(
                                                response.body()!!.data[reportPosition].pore.density,
                                                response.body()!!.data[reportPosition].pore.percentage,
                                                response.body()!!.data[reportPosition].pore.jzs_density,
                                                response.body()!!.data[reportPosition].pore.jzs_percentage,
                                                response.body()!!.data[reportPosition].pore.type,
                                                response.body()!!.data[reportPosition].pore.degree,
                                                response.body()!!.data[reportPosition].pore.cleanliness_type,
                                                response.body()!!.data[reportPosition].pore.score,
                                                response.body()!!.data[reportPosition].pore.jzs_score
                                        ),
                                        reportMeitu.Data.Blackhead(
                                                response.body()!!.data[reportPosition].blackhead.degree,
                                                response.body()!!.data[reportPosition].blackhead.density,
                                                response.body()!!.data[reportPosition].blackhead.percentage,
                                                response.body()!!.data[reportPosition].blackhead.score
                                        ),
                                        reportMeitu.Data.Speckle(
                                                response.body()!!.data[reportPosition].speckle.t,
                                                response.body()!!.data[reportPosition].speckle.cheek,
                                                response.body()!!.data[reportPosition].speckle.sum,
                                                response.body()!!.data[reportPosition].speckle.score,
                                                response.body()!!.data[reportPosition].speckle.degree
                                        ),
                                        reportMeitu.Data.Wrinkle(
                                                response.body()!!.data[reportPosition].wrinkle.density,
                                                response.body()!!.data[reportPosition].wrinkle.degree,
                                                response.body()!!.data[reportPosition].wrinkle.trouble_list,
                                                response.body()!!.data[reportPosition].wrinkle.percentage,
                                                response.body()!!.data[reportPosition].wrinkle.score
                                        ),
                                        reportMeitu.Data.Acne(
                                                response.body()!!.data[reportPosition].acne.t,
                                                response.body()!!.data[reportPosition].acne.cheek,
                                                response.body()!!.data[reportPosition].acne.chin,
                                                response.body()!!.data[reportPosition].acne.sum,
                                                response.body()!!.data[reportPosition].acne.zz_density,
                                                response.body()!!.data[reportPosition].acne.level,
                                                response.body()!!.data[reportPosition].acne.degree,
                                                response.body()!!.data[reportPosition].acne.possibility,
                                                response.body()!!.data[reportPosition].acne.score,
                                                response.body()!!.data[reportPosition].acne.zz_score
                                        ),
                                        reportMeitu.Data.Sensitive(
                                                response.body()!!.data[reportPosition].sensitive.type,
                                                response.body()!!.data[reportPosition].sensitive.degree,
                                                response.body()!!.data[reportPosition].sensitive.percentage,
                                                response.body()!!.data[reportPosition].sensitive.area,
                                                response.body()!!.data[reportPosition].sensitive.score
                                        ),
                                        reportMeitu.Data.BlackRimOfEye(
                                                response.body()!!.data[reportPosition].black_rim_of_eye.degree,
                                                response.body()!!.data[reportPosition].black_rim_of_eye.score
                                        )
                                )


                                /** 점 찍기 **/
                                simgak.text = "집중관심 ${simgakList.size}"
                                nabbem.text = "관심필요 ${nabbemList.size}"
                                yangho.text = "양호 ${yanghoList.size}"

                                skinDotHide()
                                skinDotShow(simgakList)

                                val dotAdapter = SkinDotAdapter(simgakList)
                                dotRecyclerView.apply {
                                    adapter = dotAdapter
                                    layoutManager = LinearLayoutManager(context)
                                }
                            } else {
                                textView85.isVisible = false
                                appCompatButton4.isVisible = false
                            }
                        }

                        else -> {
                            dialog.dismiss()
                            Toast.makeText(context, "서버 에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                    Toast.makeText(context, "피부정보 갱신완료!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            })
    }

//    fun md5(input:String): String {
//        val md = MessageDigest.getInstance("MD5")
//        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
//    }

//    fun logLargeString(str : String) {
//        if (str.length > 3000) {
//            logLargeString(str.substring(3000));
//        } else {
//
//        }
//    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun setPrgoress(score : Int, score_text : TextView, progressBar : ProgressBar){
        when(score){
            in 0..33 -> {
                score_text.setTextColor(Color.parseColor("#F05E49"))
                score_text.text = score.toString()
                progressBar.progressDrawable = activity!!.getDrawable(R.drawable.progress_red)
                progressBar.progress = score
            }
            in 34..59 -> {
                score_text.setTextColor(Color.parseColor("#F6AC1D"))
                score_text.text = score.toString()
                progressBar.progressDrawable = activity!!.getDrawable(R.drawable.progress_yellow)
                progressBar.progress = score
            }
            in 60..100 -> {
                score_text.setTextColor(Color.parseColor("#2DBCDE"))
                score_text.text = score.toString()
                progressBar.progressDrawable = activity!!.getDrawable(R.drawable.progress_blue)
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
            "집중관심 ${simgakList.size}" -> {
                skinDotHide()
                skinDotAdapter = SkinDotAdapter(simgakList)
                dotRecyclerView.apply {
                    adapter = skinDotAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                skinDotShow(simgakList)
            }

            "관심필요 ${nabbemList.size}" -> {
                skinDotHide()
                skinDotAdapter = SkinDotAdapter(nabbemList)
                dotRecyclerView.apply {
                    adapter = skinDotAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                skinDotShow(nabbemList)
            }

            "양호 ${yanghoList.size}" -> {
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

    fun getSkinScore(sensitive2 : Int, speckle2 : Int, wrinkle2 : Int, acne2 : Int, blackhead2 : Int, blackRimOfEye2 : Int, pore2 : Int){
        val dialog= ProgressDialog.progressDialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        RetrofitMansae.server.getMyScore(sensitiveScore = sensitive2, wrinkleScore = wrinkle2, acneScore = acne2, blackheadScore = blackhead2, blackRimOfEyeScore = blackRimOfEye2, poreScore = pore2, speckleScore = speckle2)
            .enqueue(object : Callback<scoreResultDTO>{
                override fun onFailure(call: Call<scoreResultDTO>, t: Throwable) {

                }

                override fun onResponse(call: Call<scoreResultDTO>, response: Response<scoreResultDTO>) {

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            sensitive = response.body()!!.data!!.sensitive!!
                            speckle = response.body()!!.data!!.speckle!!
                            wrinkle = response.body()!!.data!!.wrinkle!!
                            acne = response.body()!!.data!!.acne!!
                            blackhead = response.body()!!.data!!.blackhead!!
                            blackRimOfEye = response.body()!!.data!!.blackRimOfEye!!
                            pore = response.body()!!.data!!.pore!!

                            val skinAdapter = SkinDetailAdapter(skinDetailArrayList, context!! as Activity)
                            val skin2Adapter = SkinDetail2Adapter(skinDetail2ArrayList, context!! as Activity, pore = pore, blackhead = blackhead, blackRimOfEye = blackRimOfEye, acne = acne, wrinkle = wrinkle, speckle = speckle, sensitive = sensitive)

                            skinrecyclerview.apply {
                                adapter = skinAdapter
                                layoutManager = LinearLayoutManager(context)
                            }

                            skin2recyclerview.apply {
                                adapter = skin2Adapter
                                layoutManager = LinearLayoutManager(context)
                            }
                            dialog.dismiss()
                        }
                        else -> {
                            dialog.dismiss()
                        }
                    }

                }
            })
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
    }

    override fun onDetach() {
        super.onDetach()
        simgakList.clear()
        yanghoList.clear()
        nabbemList.clear()
        mainActivity = null
    }
}