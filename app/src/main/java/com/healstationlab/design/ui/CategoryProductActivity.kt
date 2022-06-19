package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.healstationlab.design.R
import com.healstationlab.design.adapter.Category2ProductAdpater
import com.healstationlab.design.adapter.CategoryProductAdpater
import com.healstationlab.design.adapter.ProductAdapter
import com.healstationlab.design.databinding.ActivityCategoryProductBinding
import com.healstationlab.design.dto.category
import com.healstationlab.design.dto.recommend
import com.healstationlab.design.model.CategoryProductModel
import com.healstationlab.design.model.Product
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.ProgressDialog
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class CategoryProductActivity : AppCompatActivity() {
    var categoryList : ArrayList<CategoryProductModel> = arrayListOf()
    var categorySecondList : ArrayList<CategoryProductModel> = arrayListOf()
    var recommedList : ArrayList<Product> = arrayListOf()
    var recommendAdatper : ProductAdapter? = null
    var scrollCheck = 0
    var page = 0
    var categoryId = 0

    lateinit var binding : ActivityCategoryProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityCategoryProductBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        categoryId = (intent.getIntExtra("id", 0))



        getCategory()

        binding.imageView51.setOnClickListener {
            finish()
            overridePendingTransition(0, R.xml.slide_right)
        }
        
        binding.nestedScrollView3.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ -> //                if (scrollY > oldScrollY) {
            //
            //                }
            //                if (scrollY < oldScrollY) {
            //
            //                }
            //
            //                if (scrollY == 0) {
            //
            //                }

            if (scrollY == (v!!.getChildAt(0).measuredHeight - v.measuredHeight)) {
                getRecommendAi(++page, "")
            }
        })

//        binding.recyclerView3.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                val lastVisibleItemPosition = (recyclerView.layoutManager as LinearLayoutManager?)!!.findLastCompletelyVisibleItemPosition()
//                Log.d("lastVisibleItemPosition", lastVisibleItemPosition.toString())
//
//                if(lastVisibleItemPosition == recommedList.size-1){
//                    Log.d("마지막 포지션 : ", lastVisibleItemPosition.toString())
//                    getRecommendAi(++page, "")
//                }
//            }

//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                if(recyclerView.canScrollVertically(-1)){
//                    Log.d("스크롤 마지막!", "마지막")
//                    getRecommendAi(++page, "")
//                } else {
//                    Log.d("스크롤 마지막 아님!", "마지막 아님")
//                }
//            }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    /** 1차 **/
    private fun getCategory(){
        RetrofitMansae.server.getCategory()
            .enqueue(object : Callback<category> {
                override fun onFailure(call: Call<category>, t: Throwable) {

                }

                override fun onResponse(call: Call<category>, response: Response<category>) {
                    when(response.body()!!.responseCode){
                        "SUCCESS" -> {
                            categoryList.add(CategoryProductModel("", "전체", ""))
                            for(i in response.body()!!.data){
                                if(i.name == intent.getStringExtra("title")){
                                    categoryList.add(CategoryProductModel(i.id, i.name, i.iconImageUrl, true))
                                    binding.recyclerView5.isVisible = true
                                    binding.view131.isVisible = true
                                }
                                else {
                                    categoryList.add(
                                        CategoryProductModel(i.id, i.name, i.iconImageUrl)
                                    )
                                }
                            }
                            categorySecond(categoryId)
                            val categoryProductAdapter = CategoryProductAdpater(categoryList)
                            binding.recyclerView4.apply {
                                adapter = categoryProductAdapter
                                layoutManager = LinearLayoutManager(this@CategoryProductActivity, LinearLayoutManager.HORIZONTAL, false)
                            }

                            categoryProductAdapter.setItemClickListner(object : CategoryProductAdpater.ItemClickListener{
                                @SuppressLint("NotifyDataSetChanged")
                                override fun onClick(view: View, position: Int) {
                                    for(i in 0 until categoryList.size){
                                        categoryList[i].isClick = false
                                    }
                                    categoryList[position].isClick = true
                                    categoryProductAdapter.notifyDataSetChanged()
                                    if(position != 0){
                                        binding.recyclerView5.isVisible = true
                                        binding.view131.isVisible = true
                                        categorySecond(categoryList[position].id!!)
                                    } else {
                                        recommedList.clear()
                                        binding.recyclerView5.isVisible = false
                                        binding.view131.isVisible = false
                                        getRecommendAi(page, "")
                                    }
                                }
                            })
                        }
                    }
                }
            })
    }

    /** 2차 **/
    fun categorySecond(id : Any){
        RetrofitMansae.server.getCategoryProduct(parentId = id)
            .enqueue(object : Callback<category>{
                override fun onFailure(call: Call<category>, t: Throwable) {

                }

                override fun onResponse(call: Call<category>, response: Response<category>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            categorySecondList.clear()
                            categorySecondList.add(CategoryProductModel(id, "전체", isClick = true))

                            for(i in response.body()!!.data){
                                categorySecondList.add(CategoryProductModel(i.id, i.name))
                            }

                            val categoryProduct2Adapter = Category2ProductAdpater(categorySecondList)

                            binding.recyclerView5.apply {
                                layoutManager = GridLayoutManager(context, 3)
                                adapter = categoryProduct2Adapter
                            }

                            categoryProduct2Adapter.setItemClickListner(object : Category2ProductAdpater.ItemClickListener{
                                @SuppressLint("NotifyDataSetChanged")
                                override fun onClick(view: View, position: Int) {
                                    for(i in 0 until categorySecondList.size){
                                        categorySecondList[i].isClick = false
                                    }
                                    recommedList.clear()
                                    categorySecondList[position].isClick = true
                                    categoryProduct2Adapter.notifyDataSetChanged()
                                    getRecommendAi(page, categorySecondList[position].id.toString())
                                }
                            })
                            recommedList.clear()
                            getRecommendAi(page, id.toString())
                        }

                        else -> {
                            // 서버에러
                        }
                    }
                }
            })
    }

    fun getRecommendAi(page : Int, category : String){
        val dialog= ProgressDialog.progressDialog(this)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

        RetrofitMansae.server.getRecommendAi(pageNumber = page, pageSize = 15, categoryIds = category, skinProblem = "", skinType = "",recommendationProductCode = App.prefs.getStringData(Constant.CODE).toString())
            .enqueue(object : Callback<recommend> {
                override fun onFailure(call: Call<recommend>, t: Throwable) {

                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<recommend>, response: Response<recommend>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
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

                            /** 스크롤 올림 방지 **/
                            if(scrollCheck == 0){
                                scrollCheck = 1
                                recommendAdatper = ProductAdapter(recommedList, 0)
                                binding.recyclerView3.apply {
                                    recommendAdatper.also { this.adapter = it }
                                    layoutManager = LinearLayoutManager(this@CategoryProductActivity)
                                }
                            }
//
//                            recommendAdatper = ProductAdapter(recommedList, 0)
//                            recommendAdatper!!.notifyDataSetChanged()
//                            binding.recyclerView3.apply {
//                                this!!.adapter = recommendAdatper
//                                layoutManager = LinearLayoutManager(context)
//                            }
                            recommendAdatper?.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(this@CategoryProductActivity, ProductDetailActivity::class.java)
                                    intent.putExtra("id", recommedList[position].id)
                                    startActivity(intent)
                                    overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
                                }
                            })
                            recommendAdatper!!.notifyDataSetChanged()
                            Toast.makeText(this@CategoryProductActivity, "해당 카테고리 상품이 업데이트 되었습니다!", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }

                        else -> {
                            // Toast.makeText()
                        }
                    }
                }
            })
    }
}