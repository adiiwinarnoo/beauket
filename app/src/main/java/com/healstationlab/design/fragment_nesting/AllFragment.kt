package com.healstationlab.design.fragment_nesting

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.healstationlab.design.R
import com.healstationlab.design.adapter.*
import com.healstationlab.design.dto.*
import com.healstationlab.design.model.Banner
import com.healstationlab.design.model.BannerLocal
import com.healstationlab.design.model.PopUpNewBanner
import com.healstationlab.design.model.Product
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import com.healstationlab.design.resource.ProgressDialog
import com.healstationlab.design.resource.RetrofitMansae
import com.healstationlab.design.ui.MainActivity
import com.healstationlab.design.ui.ProductDetailActivity
import com.healstationlab.design.ui.SearchActivity
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat

class AllFragment : Fragment() {

    var mainActivity: MainActivity? = null

    var restingCategoryArrayList = ArrayList<ArrayList<CategoryData>>()
    var categoryArrayList : ArrayList<CategoryData> = arrayListOf()
    var cloneList : ArrayList<CategoryData> = arrayListOf()


    var categoryviewpager : ViewPager2? = null
    var bannerViewPager : ViewPager2? = null
    val adapter = CategoryAdapter
    lateinit var indicator : CircleIndicator3

    val recommedList : ArrayList<Product> = arrayListOf() // ai추천
    val dealList : ArrayList<Product> = arrayListOf() // 특가
    val rankList : ArrayList<Product> = arrayListOf() // 랭크

    var recommendRecyclerView : RecyclerView? = null
    var saleRecyclerView : RecyclerView? = null
    var rankRecyclerView : RecyclerView? = null
    var recyclerPopup : RecyclerView? = null

    var isiArray = ArrayList<PopUpNewBanner>()

    var adapterNew : bannerShopNewAdapter? = null
    var imgArray = intArrayOf(R.drawable.banner_001,R.drawable.banner_02,R.drawable.banner_01)




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_all, container, false)
        retainInstance = true

        categoryviewpager = view.findViewById(R.id.category_viewpager)
        indicator = view.findViewById(R.id.indicator)
        bannerViewPager = view.findViewById(R.id.all_viewpager)

        recommendRecyclerView = view.findViewById(R.id.recommendRecyclerview)
        saleRecyclerView = view.findViewById(R.id.saleRecyclerview)
        rankRecyclerView = view.findViewById(R.id.rankRecyclerView)

//        val viewpager = view!!.findViewById<ViewPager>(R.id.all_viewpager_dummy)
//        adapterNew = bannerShopNewAdapter(view.context,imgArray)
//        val indicator = view!!.findViewById<CircleIndicator3>(R.id.all_indicator)
//        viewpager.adapter = adapterNew


        val usernametextview = view.findViewById<TextView>(R.id.user_name_textview)
        usernametextview.text = App.prefs.getStringData(Constant.NAME)
        val searchButton = view.findViewById<ImageView>(R.id.search_button)

        recyclerPopup = view.findViewById(R.id.recycler_new_popup_banner_shopping)
        var isi1 = PopUpNewBanner("먼저, 뷰켓을 만나기 전 피부 관리에 대해서 어떤 생각을 하셨나요?",
            "피부에 특별히 신경을 쓰진 않았어요. 그냥 지저분한 게 보이기 싫어서 쿠션으로 열심히 가리고 다녔어요")
        isiArray.add(isi1)

        for (dataIsi in isiArray){
            Log.d("isi-array", dataIsi.textQuestion.toString())
        }

        showNewPopup()


        searchButton.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
            activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//            popUpNewBanners()
        }


        indicator.setOnClickListener {
            popUpNewBanners()
        }


        getCategory()
        getRecommendAi()
        getDeal()
        getRank()
        getBanner()

        return view
    }

    private fun popUpNewBanners() {
            val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
            val menuPopup = inflater!!.inflate(R.layout.popup_new_banner_home,null)
            val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, false)
            val adapter = PopupNewBannerAdapter(
                isiArray)
            val recycleQuesAns = menuPopup.findViewById<RecyclerView>(R.id.recycler_new_popup_banner_shopping)
            recycleQuesAns?.adapter = adapter
            Log.d("isi-array", isiArray.toString())
            recycleQuesAns?.layoutManager = LinearLayoutManager(context)
            popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
            val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
            imgClose.setOnClickListener { popup.dismiss() }

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        mainActivity = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        setTargetFragment(null, -1)
    }


    /** 카테고리 **/
    private fun getCategory(){
        val dialog= ProgressDialog.progressDialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()

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



                                categoryviewpager.apply {
                                    this!!.adapter = CategoryAdapter(restingCategoryArrayList, "1", mainActivity!!)
                                }
                                indicator.setViewPager(categoryviewpager)
                                indicator.createIndicators(((restingCategoryArrayList.size)), 0)
                                dialog.dismiss()
                            }
                            else -> {
                                dialog.dismiss()
                            }
                        }
                    }
                })
    }

    /** AI 추천 **/
    private fun getRecommendAi(){
        RetrofitMansae.server.getRecommendAi(pageSize = 50, pageNumber = 0,recommendationProductCode = App.prefs.getStringData(Constant.CODE).toString())
                .enqueue(object : Callback<recommend>{
                    override fun onFailure(call: Call<recommend>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<recommend>, response: Response<recommend>) {

                        when(response.body()?.responseCode){
                            "SUCCESS" -> {
                                var cnt = 0
                                for(i in response.body()!!.data) {
                                    if(!i.hidden){
                                        cnt++
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
                                    if(cnt == 4){
                                        break
                                    }
                                }
                                val recommendAdatper = ProductAdapter(recommedList, 0)

                                recommendRecyclerView.apply {
                                    this!!.adapter = recommendAdatper
                                    layoutManager = LinearLayoutManager(context)
                                }

                                recommendAdatper.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                    override fun onClick(view: View, position: Int) {
                                        val intent = Intent(context, ProductDetailActivity::class.java)
                                        intent.putExtra("id", recommedList[position].id)
                                        startActivity(intent)
                                        activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
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

    private fun getDeal(){
        RetrofitMansae.server.getDeal(pageSize = 50, pageNumber = 0,recommendationProductCode = App.prefs.getStringData(Constant.CODE).toString())
            .enqueue(object : Callback<deal>{
                override fun onFailure(call: Call<deal>, t: Throwable) {

                }

                override fun onResponse(call: Call<deal>, response: Response<deal>) {

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            var cnt = 0

                            for(i in response.body()!!.data) {
                                if(!i.hidden){
                                    cnt++
                                    if(i.options.isEmpty()){
                                        dealList.add(
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
                                        dealList.add(
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
                                if(cnt == 4){
                                    break
                                }
                            }
                            val dealAdatper = ProductAdapter(dealList, 1)

                            saleRecyclerView.apply {
                                this!!.adapter = dealAdatper
                                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                            }

                            dealAdatper.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, ProductDetailActivity::class.java)
                                    intent.putExtra("id", dealList[position].id)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
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

    private fun getRank(){
        RetrofitMansae.server.getRank(pageSize = 50, pageNumber = 0)
            .enqueue(object : Callback<rank>{
                override fun onFailure(call: Call<rank>, t: Throwable) {

                }

                override fun onResponse(call: Call<rank>, response: Response<rank>) {
                    var cnt = 0

                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data) {
                                if(!i.hidden){
                                    cnt++
                                    if(i.options.isEmpty()){
                                        rankList.add(
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
                                        rankList.add(
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
                                if(cnt == 4){
                                    break
                                }
                            }
                            val rankAdapter = ProductAdapter(rankList, 2)

                            rankRecyclerView.apply {
                                this!!.adapter = rankAdapter
                                layoutManager = GridLayoutManager(context, 2)
                            }

                            rankAdapter.setItemClickListner(object : ProductAdapter.ItemClickListener{
                                override fun onClick(view: View, position: Int) {
                                    val intent = Intent(context, ProductDetailActivity::class.java)
                                    intent.putExtra("id", rankList[position].id)
                                    startActivity(intent)
                                    activity!!.overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
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

    private fun getBanner(){
        val dialog= ProgressDialog.progressDialog(context!!)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.show()
        RetrofitMansae.server.getBanner()
                .enqueue(object : Callback<banner>{
                    override fun onFailure(call: Call<banner>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<banner>, response: Response<banner>) {

                        when(response.body()!!.responseCode){
                            "SUCCESS" -> {
                                val imgList : ArrayList<Banner> = arrayListOf()
                                var imageList : ArrayList<BannerLocal>
                                var posisi : Int
                                imageList = ArrayList()
                                val adapterLocal = BannerLocal2Adapter(imageList)
                                for(i in response.body()!!.data){
                                    if(i.positionType == "MIDDLE"){
                                        imgList.add(Banner(i.imageUrl, i.linkUrl, i.id, i.product))
                                        imageList.add(BannerLocal(R.drawable.banner_50_home,3))
                                        imageList.add(BannerLocal(R.drawable.banner_30_home,1))
                                        imageList.add(BannerLocal(R.drawable.banner_40_home,2))

//                                        imgList.add(Banner("https://pbs.twimg.com/media/FVHozOIVIAA8KkC?format=jpg&name=4096x4096",
//                                        "",90,null))
//                                        imgList.add(Banner("https://pbs.twimg.com/media/FVHpmzUVsAABwiD?format=jpg&name=4096x4096",
//                                            "",91,null))
                                        Log.d("isi-image", imageList.size.toString())
                                    }
                                }

                                val homeAdapter = HomeAdapter(imgList)
                                val viewpager = view!!.findViewById<ViewPager2>(R.id.all_viewpager)
                                val viewpager2 = view!!.findViewById<ViewPager2>(R.id.all_viewpager)
                                val indicator = view!!.findViewById<CircleIndicator3>(R.id.all_indicator)

                                viewpager.adapter = homeAdapter
                                viewpager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                                /**Local Adapter**/
                                viewpager2.adapter = adapterLocal
                                viewpager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL

//                                indicator.setViewPager(viewpager)
                                indicator.setViewPager(viewpager2)
//                                indicator.createIndicators(imgList.size, 0)
                                indicator.createIndicators(imageList.size, 0)
                                dialog.dismiss()
                            }
                            else -> {
                                dialog.dismiss()
                            }
                        }
                    }
                })
    }

    private fun showNewPopup(){
        val inflater = context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater?
        val menuPopup = inflater!!.inflate(R.layout.popup_shopping_new,null)
        val popup = PopupWindow(menuPopup, WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.MATCH_PARENT, false)
        popup.isFocusable = true
        popup.showAtLocation(menuPopup, Gravity.CENTER,0,0)
        val imgClose = menuPopup.findViewById<ImageView>(R.id.imageClose)
        val buttonSubmit = menuPopup.findViewById<TextView>(R.id.confirm_text)
        imgClose.setOnClickListener { popup.dismiss() }
        buttonSubmit.setOnClickListener { popup.dismiss() }
    }

        override fun onDestroy() {
        super.onDestroy()
        categoryArrayList.clear()
        restingCategoryArrayList.clear()
        recommedList.clear()
        dealList.clear()
        rankList.clear()
    }

}