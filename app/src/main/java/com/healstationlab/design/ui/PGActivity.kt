@file:Suppress("DEPRECATION")

package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityPGBinding
import com.healstationlab.design.dto.postOrderDTO
import com.healstationlab.design.model.Cart
import com.healstationlab.design.model.ShippingMent
import com.healstationlab.design.model.payModel
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URISyntaxException
import java.net.URLEncoder

class PGActivity : AppCompatActivity() {
    lateinit var payList : ArrayList<Cart>
    lateinit var binding : ActivityPGBinding
    var oid = ""
    var paymentDate : Int = 0
    var purchase : Double = 0.0
    var review : Int = 0
    var gubun = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPGBinding.inflate(layoutInflater)
        setContentView(binding.root)

        oid = intent.getStringExtra("o_id")!!
        paymentDate = intent.getIntExtra("paymentDate", 0)
        purchase = intent.getDoubleExtra("purchase", 0.0)
        review = intent.getIntExtra("review", 0)
        gubun = intent.getStringExtra("gubun")!!

        binding.webview.apply {
            settings.javaScriptEnabled = true //필수설정(true)a
            settings.domStorageEnabled = true //필수설정(true)
//            settings.javaScriptCanOpenWindowsAutomatically = true //필수설정(true)
            settings.defaultTextEncodingName = "UTF-8"
            addJavascriptInterface(WebAppInterface(), "Android")
        }
        val goodsName = URLEncoder.encode(intent.getStringExtra("goodsName"), "utf-8")
        val str = "https://api.ggumnol.net/view/payment?token=${App.prefs.getStringData(Constant.AUTH)}&totalPrice=${intent.getStringExtra("totalPrice")}&paymentMethod=${intent.getStringExtra("payment")}&goodsName=${goodsName}&poid=${oid}"
        binding.webview.webViewClient = DemoWebViewClient()
//         test()
        binding.webview.loadUrl(str)
    }

    inner class DemoWebViewClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            if (!url!!.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:")) {
                val intent: Intent = try {
                    Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                } catch (ex: URISyntaxException) {
                    return false
                }

                try {
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    if (url.startsWith("ispmobile://")) {
                        showAlert()
                        return false
                    } else if (url.startsWith("intent")) {
                        return try {
                            val tempIntent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                            val strParams = tempIntent.dataString
                            val intent2 = Intent(Intent.ACTION_VIEW)
                            intent2.data = Uri.parse(strParams)
                            startActivity(intent2)
                            true
                        } catch (e1: Exception) {
                            e1.printStackTrace()
                            val intent3: Intent?
                            try {
                                intent3 = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                                val marketIntent = Intent(Intent.ACTION_VIEW)
                                marketIntent.data = Uri.parse("market://details?id=" + intent3.getPackage())
                                startActivity(marketIntent)
                            } catch (e2: Exception) {
                                e2.printStackTrace()
                            }
                            true
                        }
                    }
                }
            } else {
                view!!.loadUrl(url)
                return false
            }
            val uri = Uri.parse(url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            try {
                startActivity(intent)
                if( url.startsWith("ispmobile://")){
                    finish()
                }

            } catch (e : ActivityNotFoundException){
                if( url.startsWith("ispmobile://")){
                    view!!.loadData("<html><body></body></html>", "text/html", "euc-kr")
                    showAlert()
                    return true
                }
            }
            return true
        }
    }

    fun showAlert(){
        val alertDialog = android.app.AlertDialog.Builder(this)
                .setTitle("알림")
                .setMessage("모바일 ISP 어플리케이션이 설치되어 있지 않습니다. \\n 설치를 눌러 진행 해 주십시요.\\n 취소를 누르면 결제가 취소 됩니다.")
                .setPositiveButton("확인"){ _: DialogInterface, _: Int ->
                    binding.webview.loadUrl("http://mobile.vpay.co.kr/jsp/MISP/andown.jsp")
                    finish()
                }
                .setNegativeButton("취소"){ _: DialogInterface, _: Int ->
                    Toast.makeText(this, "(-1)결제를 취소 하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }.create()
        alertDialog.show()
    }



    inner class WebAppInterface {
        @JavascriptInterface
        fun payment(paySuccess: String?) {
            Handler().post {
                if (paySuccess != null) {
                    if (paySuccess.contains("true")) {
                        /** 여기 **/
                        payList = intent.getSerializableExtra("pay") as ArrayList<Cart>
                        val body: HashMap<String, Any> = HashMap()

                        val split = paySuccess.toString().split("P_TID=")
                        val res = split.toString().split("&")

                        val arrayList: Array<payModel?> = Array(payList.size) { null }
                        val sipping =
                            intent.getSerializableExtra("deliveryCount") as ArrayList<ShippingMent>

                        when (gubun) {
                            "cart" -> {
                                for (i in 0 until payList.size) {
                                    arrayList[i] = payModel(
                                        id = payList[i].id!!,
                                        count = payList[i].count!!,
                                        deliveryCount = sipping[i].isBool
                                    )
                                }
                            }

                            "buy" -> {
                                val del = payList[0].shippingPrice!! > 0

                                for (i in 0 until payList.size) {
                                    arrayList[i] = payModel(
                                        id = payList[i].id!!,
                                        count = payList[i].count!!,
                                        deliveryCount = del
                                    )
                                }
                            }
                        }

                        body["ptid"] = res[5].replace(" ", "").replace(",", "")
                        body["options"] = arrayList.toList() // 옵션 id, 해당 옵션 갯수
                        body["shipmentAddressId"] =
                            intent.getIntExtra("shipmentAddressId", 0) // 배송지 아이디
                        body["usePoint"] = intent.getIntExtra("point", 0) // 포인트
                        body["deliveryInstruction"] =
                            intent.getStringExtra("deliveryInstruction").toString() // 배송시 유의사항
                        body["orderNo"] = oid
                        body["paymentDate"] = paymentDate
                        body["purchase"] = purchase
                        body["review"] = review


                        when (intent.getStringExtra("payment")) { // 결제방법
                            "CARD" -> {
                                body["paymentType"] = "CREDIT_CARD"
                            }
                            "BANK" -> {
                                body["paymentType"] = "TRANSFER"
                            }
                        }
                        when (intent.getIntExtra("couponId", 0)) { // 쿠폰
                            0 -> {

                            }
                            else -> {
                                body["couponId"] = intent.getIntExtra("couponId", 0) // 쿠폰 아이디
                            }
                        }
                        postOrder(body)
                    } else {
                        // 결제 실패
                    }
                } else {
                    // 실패
                }
            }
        }
    }

    /** 주문하기 **/
    fun postOrder(body : HashMap<String, Any>){
        RetrofitMansae.server.postPurchase(body = body)
                .enqueue(object : Callback<postOrderDTO> {
                    override fun onFailure(call: Call<postOrderDTO>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<postOrderDTO>, response: Response<postOrderDTO>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {

                                val intent = Intent(this@PGActivity, OrderActivity::class.java)
                                intent.putExtra("id", response.body()!!.data)
                                intent.putExtra("check", true)
                                intent.putExtra("orderNo", oid)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                Toast.makeText(this@PGActivity, "주문이 완료되었습니다.", Toast.LENGTH_SHORT).show()
                                // 성공시 성공화면으로 이동
                            }
                            else -> {
                                Toast.makeText(this@PGActivity, "서버오류", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun test() {
        payList = intent.getSerializableExtra("pay") as ArrayList<Cart>
//        val body : HashMap<String, Any> = HashMap()

//        var split = paySuccess.toString().split("P_TID=")
//        var res = split.toString().split("&")
//        var real = res

        val arrayList : Array<payModel?> = Array(payList.size) { null}
        val sipping = intent.getSerializableExtra("deliveryCount") as ArrayList<ShippingMent>

        when(gubun){
            "cart" -> {
                for(i in 0 until payList.size){
                    arrayList[i] = payModel(
                        id = payList[i].id!!,
                        count = payList[i].count!!,
                        deliveryCount = sipping[i].isBool)

                }
            }

            "buy" -> {
                val del = payList[0].shippingPrice!! > 0

                for(i in 0 until payList.size){
                    arrayList[i] = payModel(
                        id = payList[i].id!!,
                        count = payList[i].count!!,
                        deliveryCount = del)

                }
            }
        }


//
//        body["ptid"] = real[5].replace(" ", "").replace(",", "")
//        body["options"] = arrayList.toList() // 옵션 id, 해당 옵션 갯수
//        body["shipmentAddressId"] = intent.getIntExtra("shipmentAddressId", 0) // 배송지 아이디
//        body["usePoint"] = intent.getIntExtra("point", 0) // 포인트
//        body["deliveryInstruction"] = intent.getStringExtra("deliveryInstruction").toString() // 배송시 유의사항
//        Log.d("oid : ", oid)
//        body["orderNo"] = oid
//        body["paymentDate"] = paymentDate
//        body["purchase"] = purchase
//        body["review"] = review


//        when(intent.getStringExtra("payment")){ // 결제방법
//            "CARD" -> {body["paymentType"] = "CREDIT_CARD" }
//            "BANK" -> {body["paymentType"] = "TRANSFER" }
//        }
//        when (intent.getIntExtra("couponId", 0)) { // 쿠폰
//            0 -> {
//
//            }
//            else -> {
//                body["couponId"] = intent.getIntExtra("couponId", 0) // 쿠폰 아이디
//            }
//        }
//        postOrder(body)
    }
}


