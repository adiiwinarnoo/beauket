package com.healstationlab.design.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivitySignUpPopupBinding
import com.healstationlab.design.dto.auth
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.MainActivity
import com.healstationlab.design.ui.report.ReportActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpPopupActivity : AppCompatActivity() {
    lateinit var binding : ActivitySignUpPopupBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when(App.prefs.getBooleanData(Constant.MEITU_DATA_CHECK)) {
            true -> {
                visible(false)
                binding.contentText.text = "환영합니다, ${App.prefs.getStringData(Constant.NAME)}님\n\n저희 서비스를 이용해주셔서 감사합니다.\n\n${App.prefs.getStringData(Constant.STORE_NAME)}를 연결했습니다.\n"
            }

            false -> {
                visible(true)
                binding.contentText.text = "환영합니다, ${App.prefs.getStringData(Constant.NAME)}님\n\n" +
                        "저희 서비스를 이용해주셔서 감사합니다.\n\n" +
                        "더 나은 서비스를 위해\n" +
                        "뷰켓 오프라인 스토어로 가주세요"
            }
        }

        /** 메이투 값이 있을 경우 **/
        binding.confirmView.setOnClickListener {
            login()
        }

        /** 없을 경우 (정보입력)**/
        binding.inpuInfoText.setOnClickListener {
            val body : HashMap<String, String> = HashMap()
            if(App.prefs.getStringData(Constant.AUTH_TYPE) == "ID"){
                body["id"] = App.prefs.getStringData(Constant.EMAIL)!!
                body["password"] = App.prefs.getStringData(Constant.PASS_WORD)!!
                body["authType"] = "ID"
            } else {
                body["id"] = App.prefs.getStringData(Constant.AUTH_ID).toString()
                body["authType"] = App.prefs.getStringData(Constant.AUTH_TYPE).toString()
            }


            RetrofitMansae.server.login(body = body)
                .enqueue(object : Callback<auth> {
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                App.prefs.putStringData(Constant.AUTH, response.body()!!.data!!.token)
                                val intent = Intent(this@SignUpPopupActivity, ReportActivity::class.java)
                                startActivity(intent)
                                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                            }

                            "FAIL" -> {
                                Toast.makeText(this@SignUpPopupActivity, "아이디 혹은 비밀번호가 다릅니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
        }

        /** 닫기 **/
        binding.cancelText.setOnClickListener {
            login()
        }
    }

    private fun visible(bool : Boolean){
        binding.horizntalBar.isVisible = bool
        binding.inpuInfoText.isVisible = bool
        binding.cancelText.isVisible = bool
        binding.confrimText.isVisible = !bool
    }

    fun login(){
        val body : HashMap<String, String> = HashMap()
        if(App.prefs.getStringData(Constant.AUTH_TYPE) == "ID"){
            body["id"] = App.prefs.getStringData(Constant.EMAIL)!!
            body["password"] = App.prefs.getStringData(Constant.PASS_WORD)!!
            body["authType"] = "ID"
        } else {
            body["id"] = App.prefs.getStringData(Constant.AUTH_ID).toString()
            body["authType"] = App.prefs.getStringData(Constant.AUTH_TYPE).toString()
        }


        RetrofitMansae.server.login(body = body)
                .enqueue(object : Callback<auth> {
                    override fun onFailure(call: Call<auth>, t: Throwable) {
                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                App.prefs.putBooleanData(Constant.autoLogin, true)
                                App.prefs.putStringData(Constant.AUTH, response.body()!!.data!!.token)
                                App.prefs.putIntData(Constant.UID, response.body()!!.data!!.id)

                                val intent = Intent(this@SignUpPopupActivity, MainActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                                startActivity(intent)
                                finish()
                            }

                            "FAIL" -> {
                                Toast.makeText(this@SignUpPopupActivity, "아이디 혹은 비밀번호가 다릅니다. 앱을 삭제하고 다시 다운로드 해주세요!", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                })
    }
}