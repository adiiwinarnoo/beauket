@file:Suppress("SameParameterValue")

package com.healstationlab.design.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySelectLoginBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.dto.kakao
import com.healstationlab.design.dto.naverDTO
import com.healstationlab.design.dto.userInfo
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.resource.service.UserInterface
import com.healstationlab.design.ui.MainActivity
import com.healstationlab.design.ui.NiceIdActivity
import com.healstationlab.design.ui.ShemeActivity
import com.kakao.sdk.user.UserApiClient
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SelectLoginActivity : AppCompatActivity() {

    lateinit var binding : ActivitySelectLoginBinding
    lateinit var mOAuthLoginInstance : OAuthLogin
    lateinit var mContext: Context
    var id = ""
    var email = ""
    var type = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySelectLoginBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.imageView32.setOnClickListener {
            val intent = Intent(this, ShemeActivity::class.java)
            startActivity(intent)
        }

        setGooglePlusButtonText(binding.appCompatButton6, "Google 계정으로 로그인")

        /** 네이버 로그인 **/
        binding.naverLoginButton.setOnClickListener {
            val naverclientid = "ueolV6E7F2efmHAO8WBh"
            val naverclientsecret = "TyruYs6Diu"
            val naverclientname = "만세"

            mContext = this

            mOAuthLoginInstance = OAuthLogin.getInstance()
            mOAuthLoginInstance.init(
                mContext,
                naverclientid,
                naverclientsecret,
                naverclientname
            )
            val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
            object : OAuthLoginHandler() {
                override fun run(success: Boolean) {
                    if (success) {
                        type = "NAVER"
                        naver(token = mOAuthLoginInstance.getAccessToken(baseContext).toString())

                    } else {
                        val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                        val errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext)
                        Toast.makeText(
                            baseContext,
                            "errorCode:$errorCode, errorDesc:$errorDesc",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            mOAuthLoginInstance.startOauthLoginActivity(this, mOAuthLoginHandler)
        }

            /** 로그인 **/
            binding.loginButton.setOnClickListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            }

            /** 회원가입 **/
            binding.signUpTextview.setOnClickListener {
                val intent = Intent(this, TermOfServiceActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            }

        /** 카카오 로그인 **/
        binding.appCompatButton5.setOnClickListener {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
                }
                else if (token != null) {
                    type = "KAKAO"
                    Constant.kakaoToken = token.accessToken
                    kakao(token = token.accessToken)
                }
            }
        }


        /** 구글 로그인 **/
        binding.appCompatButton6.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail() // email addresses도 요청함
                    .build()
            val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
            val intent = mGoogleSignInClient.signInIntent
            startActivityForResult(intent, 1000)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val acct = completedTask.getResult(ApiException::class.java)
            if (acct != null) {
                type = "GOOGLE"
                val personEmail = acct.email
                val personId = acct.id
                App.prefs.putStringData(Constant.AUTH_ID, personId)

                email = personEmail!!
                id = personId!!

                snsLogin(personId, type)

            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            1000 -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            }
            else -> {
            }
        }
    }

    private fun setGooglePlusButtonText(signInButton: SignInButton, buttonText: String?) {
        // Find the TextView that is inside of the SignInButton and set its text
        for (i in 0 until signInButton.childCount) {
            val v: View = signInButton.getChildAt(i)
            if (v is TextView) {
                v.text = buttonText
                v.typeface = Typeface.createFromAsset(assets, "font/notosans_medium.otf")
                v.setPadding(5, 0, 10, 0)
                return
            }
        }
    }

    fun snsLogin(id : String, type : String){
        val body : HashMap<String, String> = HashMap()
        body["id"] = id
        body["authType"] = type
        RetrofitMansae.server.login(body)
            .enqueue(object : Callback<auth>{
                override fun onFailure(call: Call<auth>, t: Throwable) {

                }

                override fun onResponse(call: Call<auth>, response: Response<auth>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            getLogin()
                        }
                        /** 회원가입 **/
                        "FAIL" -> {
                            val intent = Intent(this@SelectLoginActivity, NiceIdActivity::class.java)
                            intent.putExtra("SNS", type)
                            intent.putExtra("email", email)
                            intent.putExtra("id", id)
                            startActivity(intent)
                        }
                        else -> {
                            Toast.makeText(this@SelectLoginActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    fun getLogin(){
        val body : HashMap<String, String> = HashMap()
        body["id"] = App.prefs.getStringData(Constant.AUTH_ID).toString()
        body["authType"] = type
        RetrofitMansae.server.login(body = body)
                .enqueue(object : Callback<auth> {
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                        Toast.makeText(this@SelectLoginActivity, "서버 에러 관리자에게 문의해주세요.", Toast.LENGTH_SHORT).show()
                        finish()
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                App.prefs.putStringData(Constant.AUTH, response.body()!!.data!!.token)
                                App.prefs.putBooleanData(Constant.autoLogin, true)
                                App.prefs.putStringData(Constant.EMAIL, response.body()?.data?.email.toString())
                                App.prefs.putStringData(Constant.NAME, response.body()!!.data!!.name)
                                App.prefs.putStringData(Constant.NICK_NAME, response.body()!!.data!!.nickname)
                                App.prefs.putStringData(Constant.AUTH_TYPE, response.body()!!.data!!.authType)
                                App.prefs.putStringData(Constant.AUTH_ID, response.body()!!.data!!.username)
                                getUser()

                                val intent = Intent(this@SelectLoginActivity, MainActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                finish()
                            }

                            "FAIL" -> {
                                Toast.makeText(this@SelectLoginActivity, response.body()?.message.toString(), Toast.LENGTH_SHORT).show()
                                App.prefs.putBooleanData(Constant.autoLogin, false)
                                val intent = Intent(this@SelectLoginActivity, SelectLoginActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                finish()
                            }

                            else -> {

                                Toast.makeText(this@SelectLoginActivity, "서버 에러", Toast.LENGTH_SHORT).show()
                                //finish()
                            }
                        }
                    }
                })
    }

    fun getUser(){
        RetrofitMansae.server.getUserInfo()
                .enqueue(object : Callback<userInfo>{
                    override fun onFailure(call: Call<userInfo>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<userInfo>, response: Response<userInfo>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                if(response.body()?.data?.recommendProductCode != null){
                                    val rpc = response.body()?.data?.recommendProductCode.toString()
                                    val rpcsplit = rpc.split("A") // O7S7P2
                                    val newRPC = rpcsplit[0] + rpcsplit[1].removeRange(0,1)
                                    App.prefs.putStringData(Constant.CODE, newRPC)
                                }
                                if(response.body()?.data?.skinType != null){
                                    App.prefs.putStringData(Constant.MANSAE_SKIN_TYPE, response.body()?.data?.skinType)
                                }
                                if(response.body()?.data?.skinProblems!!.isNotEmpty()){
                                    App.prefs.putStringData(Constant.SKIN_PROBLEM, response.body()?.data?.skinProblems?.get(0))
                                }
                                App.prefs.putStringData(Constant.NAME, response.body()?.data?.name.toString())
                                App.prefs.putStringData(Constant.EMAIL, response.body()?.data?.email.toString())
                                App.prefs.putStringData(Constant.PHONE, response.body()?.data?.contact.toString())
                            }
                        }
                    }
                })
    }

    private fun kakao(token : String){
        val retrofit = Retrofit.Builder()
                .baseUrl(Constant.KAKAO)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val server: UserInterface = retrofit.create(UserInterface::class.java)
        server.getInfo(Authorization = "Bearer $token")
                .enqueue(object : Callback<kakao> {
                    override fun onFailure(call: Call<kakao>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<kakao>, response: Response<kakao>) {
                        when(response.code()){
                            200 -> {
                                id = response.body()!!.id.toString() // third
                                App.prefs.putStringData(Constant.AUTH_ID, id)
                                snsLogin(id, type)
                            }
                            else -> {
                                Toast.makeText(this@SelectLoginActivity, "서버에러", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }

    fun naver(token : String){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constant.NAVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val server: UserInterface = retrofit.create(UserInterface::class.java)
        server.getNaverInfo(Authorization = "bearer $token")
            .enqueue(object : Callback<naverDTO> {
                override fun onFailure(call: Call<naverDTO>, t: Throwable) {
                }

                override fun onResponse(call: Call<naverDTO>, response: Response<naverDTO>) {
                    when(response.body()?.message){
                        "success" -> {
                            id = response.body()!!.response!!.id.toString() // third
                            App.prefs.putStringData(Constant.AUTH_ID, id)
                            if(response.body()?.response?.email != null){
                                email = response.body()!!.response!!.email.toString()
                            }
                            snsLogin(id, type)
                        }
                        else -> {Toast.makeText(this@SelectLoginActivity, "서버에러", Toast.LENGTH_SHORT).show()}
                    }
                }
            })
    }
}
