package com.healstationlab.design.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.healstationlab.design.R
import com.healstationlab.design.dto.pageCount
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.fragment_nesting.JJimFragment
import com.healstationlab.design.fragment_nesting.OrderFragment
import com.healstationlab.design.fragment_nesting.SkinFragment
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.*
import de.hdodenhof.circleimageview.CircleImageView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class MyPageFragment : Fragment() {


    val skinFragment = SkinFragment()
    val orderFragment = OrderFragment()
    val jjimFragment = JJimFragment()

    var skintype = ""
    var productCode = ""

    lateinit var textView142 : TextView
    lateinit var textView150 : TextView
    lateinit var textView147 : TextView
    lateinit var textView149 : TextView
    lateinit var textView83 : TextView
    lateinit var circleImageView : CircleImageView
    private lateinit var useragetype : TextView

    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_my_page, container, false)

        val fragmentManager = childFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        val username = view.findViewById<TextView>(R.id.user_name)
        useragetype = view.findViewById(R.id.user_age_type)

        val couponbutton = view.findViewById<ImageView>(R.id.coupon_button)
        val orderbutton = view.findViewById<ImageView>(R.id.order_button)
        val reviewbutton = view.findViewById<ImageView>(R.id.review_button)
        val cartbutton = view.findViewById<ImageView>(R.id.cart_button)

        textView83 = view.findViewById(R.id.textView83)
        textView142 = view.findViewById(R.id.textView142)
        textView150 = view.findViewById(R.id.textView150)
        textView147 = view.findViewById(R.id.textView147)
        textView149 = view.findViewById(R.id.textView149)
        circleImageView = view.findViewById(R.id.circleImageView)


        skintype = App.prefs.getStringData(Constant.SKIN_TYPE)!!
        skintype = when(skintype){
            "1", "DRY" -> "건조성 피부"
            "2", "NEUTRAL" -> "중성 피부"
            "3", "OILY" -> "지성 피부 "
            "4", "COMPOSITE" -> "복합성 피부"
            else -> "피부타입 없음"
        }
        username.text = App.prefs.getStringData(Constant.NAME)+"님"

        /** tab setting **/
        val tabLayout = view.findViewById<TabLayout>(R.id.my_page_tab)

        if(Constant.ORDER){
            tabLayout.getTabAt(1)?.select()
            fragmentTransaction.replace(R.id.my_page_frame, orderFragment).commit()
            Constant.ORDER = false
        } else {
            fragmentTransaction.replace(R.id.my_page_frame, skinFragment).commit()
        }

        /** 리뷰 **/
        reviewbutton.setOnClickListener {
            if(textView142.text.toString().toInt() > 0){
                val intent = Intent(context, MyReviewActivity::class.java)
                startActivity(intent)
                activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            }
        }

        /** 쿠폰 **/
        couponbutton.setOnClickListener {
            val intent = Intent(context, CouponActivity::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 주문 **/
        orderbutton.setOnClickListener {
            val intent = Intent(context, InqueryActivity::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 장바구니 **/
        cartbutton.setOnClickListener {
            val intent = Intent(context, CartActivity::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }
        setStyleForTab(tabLayout.getTabAt(0)!!, Typeface.BOLD)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.let {
                    setStyleForTab(it, Typeface.NORMAL)
                }
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                val transaction : FragmentTransaction = fragmentManager.beginTransaction()
                tab?.let {
                    setStyleForTab(it, Typeface.BOLD)
                }
                when(tab!!.position){
                    0 -> {
                        transaction.replace(R.id.my_page_frame, skinFragment).commit()
                    }
                    1 -> transaction.replace(R.id.my_page_frame, orderFragment).commit()
                    2 -> transaction.replace(R.id.my_page_frame, jjimFragment).commit()
                    3 -> {
                        val intent = Intent(context, EditProfileActivity::class.java)
                        startActivity(intent)
                        activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                    }
                }
            }
        })
        getUser()
        getCount()
        return view
    }

    fun setStyleForTab(tab: TabLayout.Tab, style: Int) {
        tab.view.children.find { it is TextView }?.let { tv ->
            (tv as TextView).post {
                tv.setTypeface(null, style)
            }
        }
    }

    private fun getCount(){
        RetrofitMansae.server.myPageCount()
            .enqueue(object : Callback<pageCount>{
                override fun onFailure(call: Call<pageCount>, t: Throwable) {

                }

                override fun onResponse(call: Call<pageCount>, response: Response<pageCount>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            textView142.text = response.body()!!.data.reviewCount.toString()
                            textView150.text = response.body()!!.data.orderCount.toString()
                            textView147.text = response.body()!!.data.couponCount.toString()
                            textView149.text = response.body()!!.data.cartItemCount.toString()
                        }
                    }
                }
            })
    }

    private fun getUser(){
        RetrofitMansae.server.getUserInfo()
            .enqueue(object : Callback<userInfo>{
                override fun onFailure(call: Call<userInfo>, t: Throwable) {

                }

                @SuppressLint("SetTextI18n")
                override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            textView83.text = sliceAmountNumber(response.body()!!.data.points.toLong())+"P"
                            if(response.body()?.data?.imageUrl != null && response.body()?.data?.imageUrl != ""){
                                Glide.with(context!!).load(response.body()!!.data.imageUrl).into(circleImageView)
                            }
                            val split = response.body()!!.data.birthDate.split("-")
                            skintype = when(response.body()!!.data.skinType.toString()){
                                "DRY" -> "건조성 피부"
                                "NEUTRAL" -> "중성 피부"
                                "OILY" -> "지성 피부 "
                                "COMPOSITE" -> "복합성 피부"
                                else -> "피부타입 없음"
                            }
                            productCode = response.body()!!.data.recommendProductCode.toString()
                            useragetype.text = (2021-split[0].toInt()+1).toString()+"세 / " + skintype + " / " + productCode
                        }
                    }
                }
            })
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}