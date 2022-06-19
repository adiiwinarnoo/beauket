package com.healstationlab.design.ui.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityTermOfServiceBinding
import com.healstationlab.design.ui.NiceIdActivity

class TermOfServiceActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding : ActivityTermOfServiceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermOfServiceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.agreementArrowButton.setOnClickListener(this)
        binding.marketingArrowButton.setOnClickListener(this)
        binding.userInfoArrowButton.setOnClickListener(this)
        binding.textView46.setOnClickListener(this)
        binding.textView49.setOnClickListener(this)
        binding.textView50.setOnClickListener(this)

        /** 뒤로가기 **/
        binding.imageView134.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }

        /** 확인 버튼 **/
        binding.confirmText.setOnClickListener {
            if(binding.firstCheckbox.isChecked && binding.secondCheckbox.isChecked) {
                val intent = Intent(this, NiceIdActivity ::class.java)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//                finish()
            } else {
                Toast.makeText(this, "필수약관을 동의해주세요!", Toast.LENGTH_SHORT).show()
            }
        }

        /** 필수약관 전체동의 **/
        binding.necessaryCheckbox.setOnCheckedChangeListener { _, _ ->
            when(binding.necessaryCheckbox.isChecked){
                true -> isCheckedNecessary(true)
                false -> isCheckedNecessary(false)
            }
        }

        /** 선택약관 전체동의 **/
        binding.selectCheckbox.setOnCheckedChangeListener { _, _ ->
            when(binding.selectCheckbox.isChecked){
                true -> isCheckedSelect(true)
                false -> isCheckedSelect(false)
            }
        }

        /** 개인정보 처리 방침 **/
        binding.textView46.setOnClickListener {
            val intent = Intent(this, AgreementActivity::class.java)
            intent.putExtra("term", "개인")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        /** 상품 마케팅 **/
        binding.textView49.setOnClickListener {
            val intent = Intent(this, AgreementActivity::class.java)
            intent.putExtra("term", "상품")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)

        }

        /** 이용 약관 **/
        binding.textView50.setOnClickListener {
            val intent = Intent(this, AgreementActivity::class.java)
            intent.putExtra("term", "이용")
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)

        }
    }

    override fun onClick(arrowButton: View?) {
        when(arrowButton) {
            binding.agreementArrowButton,
            binding.marketingArrowButton,
            binding.userInfoArrowButton,
            binding.textView46,
            binding.textView49,
            binding.textView50 -> {
                val intent = Intent(this, AgreementActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
            }
        }
    }

    private fun isCheckedNecessary(bool: Boolean) {
        binding.firstCheckbox.isChecked = bool
        binding.secondCheckbox.isChecked = bool
    }

    private fun isCheckedSelect(bool : Boolean) {
        binding.thirdCheckbox.isChecked = bool
        binding.fouthCheckbox.isChecked = bool
        binding.fifthCheckbox.isChecked = bool
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}