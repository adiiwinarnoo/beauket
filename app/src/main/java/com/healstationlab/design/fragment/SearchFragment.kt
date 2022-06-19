package com.healstationlab.design.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.healstationlab.design.R
import com.healstationlab.design.dto.CategoryData
import com.healstationlab.design.dto.category
import com.healstationlab.design.resource.RetrofitMansae
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SearchFragment : Fragment() {
    private lateinit var searchRecyclerView : RecyclerView
    var restingCategoryArrayList = ArrayList<ArrayList<CategoryData>>()
    val categoryArrayList : ArrayList<CategoryData> = arrayListOf()
    var cloneList : ArrayList<CategoryData> = arrayListOf()
    var categoryviewpager : ViewPager2? = null
    lateinit var indicator : CircleIndicator3

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        searchRecyclerView = view.findViewById(R.id.searchRecyclerView)
//        val editTextTextPersonName5 = view.findViewById<EditText>(R.id.editTextTextPersonName5)

        categoryviewpager = view.findViewById(R.id.category_viewpager)
        indicator = view.findViewById(R.id.indicator)

        getCategory()


//        val search_button = view.findViewById<Button>(R.id.search_button)

        /** 스피너 **/
//        val skinTypeArrayList = arrayListOf<String>("피부타입", "건성", "중성", "지성", "복합성")
//        val skinProblemArrayList = arrayListOf<String>("피부문제", "모공", "블랙헤드", "색소침착", "주름", "여드름", "민감도", "다크서클")

//        val typeSpinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, skinTypeArrayList)
//        val skinSpinnerAdapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, skinProblemArrayList)

//        val typeSpinner = view.findViewById<Spinner>(R.id.skin_type_spinner).apply {
//            adapter = typeSpinnerAdapter
//            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//
//                }
//
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
////                    (parent?.getChildAt(0) as TextView).textSize = 14f
////                    (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#929292"))
//                    /** 글자크기, 글자색상 구현 **/
//                }
//            }
//        }

//        val skinSpinner = view.findViewById<Spinner>(R.id.skin_problem_spinner).apply {
//            adapter = skinSpinnerAdapter
//            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//                override fun onNothingSelected(p0: AdapterView<*>?) {
//
//                }
//
//                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, p3: Long) {
////                    (parent!!.getChildAt(0) as TextView).textSize = 14f
////                    (parent.getChildAt(0) as TextView).setTextColor(Color.parseColor("#929292"))
//                }
//            }
//        }

//        search_button.setOnClickListener {
//            if(editTextTextPersonName5.text.toString().isNotEmpty()) {
//                searchRecyclerView.apply {
//                    adapter = recommendAdatper
//                    layoutManager = LinearLayoutManager(context)
//                }
//
//                recommendAdatper.setItemClickListner(object : ProductAdapter.ItemClickListener{
//                    override fun onClick(view: View, position: Int) {
//                        val intent = Intent(context!!, ProductDetailActivity::class.java)
//                        startActivity(intent)
//                        activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//                    }
//                })
//            }
//            else {
//                Toast.makeText(context, "검색어를 입력해주세요!", Toast.LENGTH_SHORT).show()
//            }
//        }
        return view
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

//                                category_viewpager.apply {
//                                    this!!.adapter = CategoryAdapter(restingCategoryArrayList)
//                                }

                                indicator.setViewPager(categoryviewpager)
                                indicator.createIndicators(restingCategoryArrayList.size, 0)
                            }
                        }
                    }
                })
    }

    override fun onDestroy() {
        super.onDestroy()
        categoryArrayList.clear()
        restingCategoryArrayList.clear()
    }
}