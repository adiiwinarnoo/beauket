package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.CategoryAdapter
import com.healstationlab.design.adapter.ProductAdapter
import com.healstationlab.design.databinding.ActivitySearchBinding
import com.healstationlab.design.dto.CategoryData
import com.healstationlab.design.dto.category
import com.healstationlab.design.dto.recommend
import com.healstationlab.design.model.Product
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class SearchActivity : AppCompatActivity() {
    lateinit var binding : ActivitySearchBinding
    val recommedList : ArrayList<Product> = arrayListOf() // ai추천
    var restingCategoryArrayList = ArrayList<ArrayList<CategoryData>>()
    val categoryArrayList : ArrayList<CategoryData> = arrayListOf()
    var cloneList : ArrayList<CategoryData> = arrayListOf()
    lateinit var imm : InputMethodManager

    var skinType = ""
    var skinTrouble = ""
    var age = ""

    var name = ""

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.frameLayout7.setOnClickListener {
            hideKeyboard()
        }

        binding.editTextTextPersonName9.setText(App.prefs.getStringData(Constant.AGE))

        binding.searchButton.setOnClickListener {
            getRecommendAi(binding.editTextTextPersonName5.text.toString())
        }

        val split = App.prefs.getStringData(Constant.BIRTH)!!.split("-")
        binding.editTextTextPersonName9.setText((2021-split[0].toInt()+1).toString())

        name = intent.getStringExtra("title").toString()
        when (name) {
            "null" -> {
                binding.editTextTextPersonName5.setText("")
            }
            "전체" -> {
                binding.editTextTextPersonName5.setText("")
                getRecommendAi("")
            }
            else -> {
                binding.editTextTextPersonName5.setText(name)
                getRecommendAi(name)
            }
        }
        getCategory()

        binding.imageView23.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }

        /** 스피너 **/
        val skinTypeArrayList = arrayListOf("피부타입", "건성", "중성", "지성", "복합성")
        val skinProblemArrayList = arrayListOf("피부문제", "모공", "블랙헤드", "색소침착", "주름", "여드름", "민감도", "다크서클")

        val typeSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, skinTypeArrayList)
        val skinSpinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, skinProblemArrayList)


        binding.skinTypeSpinner.apply {
            adapter = typeSpinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                    // binding.skinTypeSpinner.setSelection(App.prefs.getStringData(Constant.SKIN_TYPE)!!.toInt(), true)
                    when(position){
                        0 -> {skinType = ""}
                        1 -> {skinType = "DRY"}
                        2 -> {skinType = "NEUTRAL"}
                        3 -> {skinType = "OILY"}
                        4 -> {skinType = "COMPOSITE"}
                    }
                }
            }
        }

        binding.skinProblemSpinner.apply {
            adapter = skinSpinnerAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
//                    (parent!!.getChildAt(0) as TextView).textSize = 14f
//                    (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#929292"))
                    when(position){
                        0 -> {skinTrouble = ""}
                        1 -> {skinTrouble = "PORE"}
                        2 -> {skinTrouble = "BLACK_HEAD"}
                        3 -> {skinTrouble = "SHADE_SPOT"}
                        4 -> {skinTrouble = "WRINKLE"}
                        5 -> {skinTrouble = "PIMPLE"}
                        6 -> {skinTrouble = "SENSITIVITY"}
                        7 -> {skinTrouble = "DARK_CIRCLE"}
                    }
                }
            }
        }
    }

    fun hideKeyboard() {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.editTextTextPersonName5.windowToken, 0)
    }

    private fun getCategory(){
        RetrofitMansae.server.getCategory()
            .enqueue(object : Callback<category> {
                override fun onFailure(call: Call<category>, t: Throwable) {

                }

                override fun onResponse(call: Call<category>, response: Response<category>) {

                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {
                            var num = 0
                            for(i in response.body()!!.data){
                                if(num==8){
                                    cloneList = categoryArrayList.clone() as ArrayList<CategoryData>

                                    restingCategoryArrayList.add(cloneList)
                                    num = 0
                                    categoryArrayList.clear()
                                } else {
                                    categoryArrayList.add(CategoryData(i.id, i.name, i.iconImageUrl))
                                    num++
                                }
                            }

                            if(num != 0){
                                restingCategoryArrayList.add(categoryArrayList)
                            }

                                binding.categoryViewpager.apply {
                                    this.adapter = CategoryAdapter(restingCategoryArrayList, "0", this@SearchActivity)
                                }

                            binding.indicator.setViewPager(binding.categoryViewpager)
                            binding.indicator.createIndicators(restingCategoryArrayList.size, 0)
                        }
                    }
                }
            })
    }

    fun getRecommendAi(name : String){


        if(App.prefs.getStringData(Constant.CODE).toString() != "" || App.prefs.getStringData(Constant.CODE).toString() != "null"){
            RetrofitMansae.server.getRecommendAi(pageSize = 50, pageNumber = 0, keyword = name, ageFrom = binding.editTextTextPersonName9.text.toString().toInt(), recommendationProductCode = App.prefs.getStringData(Constant.CODE).toString(),
                    ageTo = binding.editTextTextPersonName9.text.toString().toInt(), categoryIds = Constant.categoryId, skinType = skinType, skinProblem = skinTrouble)
                    .enqueue(object : Callback<recommend>{
                        override fun onFailure(call: Call<recommend>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<recommend>, response: Response<recommend>) {
                            when(response.body()?.responseCode){
                                "SUCCESS" -> {
                                    /** 키보드 내림 처리 **/
                                    imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                                    imm.hideSoftInputFromWindow(binding.editTextTextPersonName5.windowToken, 0)
                                    Toast.makeText(this@SearchActivity, "검색완료!", Toast.LENGTH_SHORT).show()
                                    recommedList.clear()
                                    for(i in response.body()!!.data) {
                                        if(!i.hidden){
                                            if(i.options.isEmpty()){
                                                recommedList.add(
                                                        Product(
                                                                i.id,
                                                                i.imageUrl,
                                                                if(i.cosSimilarity == null){
                                                                    0.0
                                                                } else {
                                                                    DecimalFormat("#.#").format(i.cosSimilarity.ratings!!).toDouble()
                                                                },
                                                                i.brand,
                                                                i.name,
                                                                ""
                                                        )
                                                )
                                            } else {
                                                recommedList.add(
                                                        Product(
                                                                i.id,
                                                                i.imageUrl,
                                                                if(i.cosSimilarity == null){
                                                                    0.0
                                                                } else {
                                                                    DecimalFormat("#.#").format(i.cosSimilarity.ratings!!).toDouble()
                                                                },
                                                                i.brand,
                                                                i.name,
                                                                i.options[0].price.toString()
                                                        )
                                                )
                                            }
                                        }
                                    }
                                    val recommendAdatper = ProductAdapter(recommedList, 0)

                                    binding.searchRecyclerView.apply {
                                        adapter = recommendAdatper
                                        layoutManager = LinearLayoutManager(context)
                                    }

                                    recommendAdatper.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                        override fun onClick(view: View, position: Int) {
                                            val intent = Intent(this@SearchActivity, ProductDetailActivity::class.java)
                                            intent.putExtra("id", recommedList[position].id)
                                            startActivity(intent)
                                            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                        }
                                    })
                                }

                                else -> {
                                    // Toast.makeText()
                                }
                            }
                        }
                    })
        } else {
            RetrofitMansae.server.getRecommendAi(pageSize = 50, pageNumber = 0, keyword = name, ageFrom = binding.editTextTextPersonName9.text.toString().toInt(), recommendationProductCode = "",
                    ageTo = binding.editTextTextPersonName9.text.toString().toInt(), categoryIds = Constant.categoryId, skinType = skinType, skinProblem = skinTrouble)
                    .enqueue(object : Callback<recommend>{
                        override fun onFailure(call: Call<recommend>, t: Throwable) {

                        }

                        override fun onResponse(call: Call<recommend>, response: Response<recommend>) {
                            when(response.body()?.responseCode){
                                "SUCCESS" -> {
                                    /** 키보드 내림 처리 **/
                                    imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                                    imm.hideSoftInputFromWindow(binding.editTextTextPersonName5.windowToken, 0)
                                    Toast.makeText(this@SearchActivity, "검색완료!", Toast.LENGTH_SHORT).show()
                                    recommedList.clear()
                                    for(i in response.body()!!.data) {
                                        if(!i.hidden){
                                            if(i.options.isEmpty()){
                                                recommedList.add(
                                                        Product(
                                                                i.id,
                                                                i.imageUrl,
                                                                i.rating,
                                                                i.brand,
                                                                i.name,
                                                                ""
                                                        )
                                                )
                                            } else {
                                                recommedList.add(
                                                        Product(
                                                                i.id,
                                                                i.imageUrl,
                                                                i.rating,
                                                                i.brand,
                                                                i.name,
                                                                i.options[0].price.toString()
                                                        )
                                                )
                                            }
                                        }
                                    }
                                    val recommendAdatper = ProductAdapter(recommedList, 0)

                                    binding.searchRecyclerView.apply {
                                        adapter = recommendAdatper
                                        layoutManager = LinearLayoutManager(context)
                                    }

                                    recommendAdatper.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                        override fun onClick(view: View, position: Int) {
                                            val intent = Intent(this@SearchActivity, ProductDetailActivity::class.java)
                                            intent.putExtra("id", recommedList[position].id)
                                            startActivity(intent)
                                            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                        }
                                    })
                                }

                                else -> {
                                    // Toast.makeText()
                                }
                            }
                        }
                    })
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
        Constant.categoryId = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        Constant.categoryId = ""
    }

}