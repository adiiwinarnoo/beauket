package com.healstationlab.design.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.healstationlab.design.R
import com.healstationlab.design.databinding.ActivityProductUseBinding
import okio.blackholeSink

class ProductUseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityProductUseBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val FirstCategori : ArrayList<String> = ArrayList()
        val FirstCategori2 : ArrayList<String> = ArrayList()
        val secondCategori : ArrayList<String> = ArrayList()
        val secondCategori1 : ArrayList<String> = ArrayList()
        val secondCategori2 : ArrayList<String> = ArrayList()
        val secondCategori3 : ArrayList<String> = ArrayList()
        val secondCategori4 : ArrayList<String> = ArrayList()
        val secondCategori5 : ArrayList<String> = ArrayList()




            FirstCategori.add("화장품 분류를 선택하세요")
            FirstCategori.add("스킨케어")
            FirstCategori.add("클렌징")
            FirstCategori.add("마스크팩")
            FirstCategori.add("선케어")
            FirstCategori.add("립케어")
            FirstCategori.add("바디케어")

        //
            secondCategori.add("스킨/토너")
            secondCategori.add("로션/에멀젼")
            secondCategori.add("에센스/세럼")
            secondCategori.add("크림")
            secondCategori.add("페이스오일")
            secondCategori.add("스팟/패치")
        //
        secondCategori1.add("립밤")
        secondCategori1.add("컬러립밤")
        secondCategori1.add("립오일")
        secondCategori1.add("립스크럽")
        secondCategori1.add("립마스크")
        //
        secondCategori2.add("페이셜클렌저")
        secondCategori2.add("메이크업클렌저")
        secondCategori2.add("포인트리무버")
        secondCategori2.add("각질케어")
        secondCategori2.add("클렌징도구")
        //
        secondCategori3.add("시트마스크")
        secondCategori3.add("페이스마스크")
        secondCategori3.add("부분마스크")
        secondCategori3.add("마사지도구")
        //
        secondCategori4.add("선크림")
        secondCategori4.add("립밤")
        secondCategori4.add("컬러립밤")
        secondCategori4.add("립오일")
        secondCategori4.add("립스크럽")
        secondCategori4.add("립마스크")

        //
        secondCategori5.add("바디크림/젤")
        //


        val adapterList = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,FirstCategori)
        val adapterList2 = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,FirstCategori2)
        val second = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,secondCategori)
        val one = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,secondCategori1)
        val third = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,secondCategori2)
        val four = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,secondCategori3)
        val five = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,secondCategori4)
        val six = ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,secondCategori5)


        binding.spinnerPUSE2.adapter = adapterList2
        binding.spinnerPUSE.adapter = adapterList
        binding.spinnerPUSE3.adapter = second

        binding.spinnerPUSE.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
              if (binding.spinnerPUSE.selectedItem.equals("스킨케어")){
                  binding.spinnerPUSE3.visibility = View.VISIBLE
                  binding.dropimg.visibility = View.VISIBLE
              }else if (binding.spinnerPUSE.selectedItem.equals("클렌징")){
                  binding.spinnerPUSE3.adapter = third
                  binding.spinnerPUSE3.visibility = View.VISIBLE
                  binding.dropimg.visibility = View.VISIBLE

              }else if (binding.spinnerPUSE.selectedItem.equals("마스크팩")){
                  binding.spinnerPUSE3.adapter = four
                  binding.spinnerPUSE3.visibility = View.VISIBLE
                  binding.dropimg.visibility = View.VISIBLE

              }else if (binding.spinnerPUSE.selectedItem.equals("선케어")){
                  binding.spinnerPUSE3.adapter = one
                  binding.spinnerPUSE3.visibility = View.VISIBLE
                  binding.dropimg.visibility = View.VISIBLE

              }else if (binding.spinnerPUSE.selectedItem.equals("립케어")){
                  binding.spinnerPUSE3.adapter = five
                  binding.spinnerPUSE3.visibility = View.VISIBLE
                  binding.dropimg.visibility = View.VISIBLE

              }else{
                  binding.spinnerPUSE3.visibility = View.GONE
                  binding.dropimg.visibility = View.GONE
              }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }






        binding.btnCheck.setOnClickListener {
            binding.btnCheck.visibility = View.INVISIBLE
            binding.btnCheck22.visibility = View.INVISIBLE
            binding.textview23.visibility = View.INVISIBLE
            binding.textview231.visibility = View.VISIBLE
            binding.btnCheck1.visibility = View.VISIBLE

        }
        binding.btnCheck2.setOnClickListener {
            binding.btnCheck2.visibility = View.INVISIBLE
            binding.textview29.visibility = View.INVISIBLE
            binding.textview291.visibility = View.VISIBLE
            binding.btnCheck1.visibility = View.INVISIBLE
            binding.btnCheck.visibility = View.VISIBLE
            binding.btnCheck22.visibility = View.VISIBLE
        }
        binding.btnCheck1.setOnClickListener {
            binding.btnCheck.visibility = View.VISIBLE
            binding.btnCheck1.visibility = View.INVISIBLE
            binding.btnCheck22.visibility = View.INVISIBLE
        }
        binding.btnCheck22.setOnClickListener {
            binding.btnCheck22.visibility = View.INVISIBLE
            binding.btnCheck2.visibility = View.VISIBLE
        }


        binding.imageView64.setOnClickListener { onBackPressed() }


    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }
}