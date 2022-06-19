@file:Suppress("DEPRECATION")

package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityNiceIdBinding
import com.healstationlab.design.ui.login.*
import java.net.URISyntaxException


class NiceIdActivity : AppCompatActivity() {
    lateinit var binding : ActivityNiceIdBinding
    private val urlinfo = "https://api.ggumnol.net/view/certification"
    var type = ""

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNiceIdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.webview.apply {
            settings.javaScriptEnabled = true //필수설정(true)
            settings.domStorageEnabled = true //필수설정(true) 
            settings.javaScriptCanOpenWindowsAutomatically = true //필수설정(true)
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.loadsImagesAutomatically = true
            settings.builtInZoomControls = true
            settings.setSupportZoom(true)
            settings.setSupportMultipleWindows(true)
            settings.loadWithOverviewMode = true
            settings.useWideViewPort = true
            addJavascriptInterface(WebAppInterface(), "Android")
        }

        binding.webview.webViewClient = DemoWebViewClient()
        binding.webview.webChromeClient = HelloWebChromeClient()
        binding.webview.loadUrl(urlinfo)
    }

    inner class HelloWebChromeClient : WebChromeClient() {
        override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult?): Boolean {
            return super.onJsAlert(view, url, message, result)
        }
    }
    inner class DemoWebViewClient : WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            //웹뷰 내 표준창에서 외부앱(통신사 인증앱)을 호출하려면 intent:// URI를 별도로 처리해줘야 합니다.

            //다음 소스를 적용 해주세요.

            if (url?.startsWith("intent://")!!) {
                var intent: Intent? = null

                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                    intent?.let { startActivity(it) }
                } catch (e: URISyntaxException) {
                    //URI 문법 오류 시 처리 구간
                } catch (e: ActivityNotFoundException) {
                    val packageName = intent?.getPackage()
                    if (packageName != "") {
                        // 앱이 설치되어 있지 않을 경우 구글마켓 이동
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                    }
                }
                //return  값을 반드시 true로 해야 합니다.
                return true
            } else if (url.startsWith("https://play.google.com/store/apps/details?id=") || url.startsWith("market://details?id=")) {
                //표준창 내 앱설치하기 버튼 클릭 시 PlayStore 앱으로 연결하기 위한 로직
                val uri = Uri.parse(url)
                val packageName = uri.getQueryParameter("id")
                if (packageName != null && packageName != "") {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName")))
                }
                return true
            } else {
                view!!.loadUrl(url)
            }
            return false
        }


        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }


        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
        }
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun passCertificationResult(result: String?) {
            Handler().post {
                if (result != null) {
                    when (intent.getBooleanExtra("check", false)) {
                        /** 비밀번호 찾기 **/
                        true -> {
                            val intent =
                                Intent(this@NiceIdActivity, NewPasswordActivity::class.java)
                            intent.putExtra("str", result)
                            startActivity(intent)
                            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                            finish()
                        }

                        /** 회원가입 **/
                        false -> {
                            val sns: String? = intent.getStringExtra("SNS")
                            val id = intent.getStringExtra("id")
                            val email = intent.getStringExtra("email")
                            when (intent.hasExtra("SNS")) {
                                true -> {
                                    val intent =
                                        Intent(this@NiceIdActivity, SNSActivity::class.java)
                                    intent.putExtra("str", result)
                                    intent.putExtra("SNS", sns)
                                    intent.putExtra("id", id)
                                    intent.putExtra("email", email)

                                    startActivity(intent)
                                    overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                    finish()
                                }
                                false -> {
                                    /** 회원가입, 아이디 찾기 **/
                                    if (intent.hasExtra("findId")) {
                                        val intent = Intent(
                                            this@NiceIdActivity,
                                            FindIdResultActivity::class.java
                                        )
                                        intent.putExtra("str", result)
                                        startActivity(intent)
                                        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                        finish()
                                    } else {
                                        val intent =
                                            Intent(this@NiceIdActivity, SingupActivity::class.java)
                                        intent.putExtra("str", result)
                                        startActivity(intent)
                                        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                        finish()
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Toast.makeText(this@NiceIdActivity, "인증코드가 다릅니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}