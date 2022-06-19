package com.healstationlab.design.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.healstationlab.design.R
import com.healstationlab.design.adapter.FuncAdapter
import com.healstationlab.design.adapter.ReviewAdapter
import com.healstationlab.design.adapter.WordReviewAdapter
import com.healstationlab.design.databinding.ActivityProductDetailBinding
import com.healstationlab.design.dto.*
import com.healstationlab.design.model.Func
import com.healstationlab.design.model.ReviewModel
import com.healstationlab.design.model.countModel
import com.healstationlab.design.resource.BottomSheet
import com.healstationlab.design.resource.RetrofitMansae
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat
import kotlin.collections.ArrayList

class ProductDetailActivity : AppCompatActivity() {

    val reviewList : ArrayList<ReviewModel> = ArrayList()
    val funcList : ArrayList<Func> = arrayListOf()
    var wordList : ArrayList<String> = arrayListOf()
    var newReviewList : ArrayList<ReviewModel> = ArrayList()

    lateinit var binding : ActivityProductDetailBinding
    var id = 0
    var url = ""

    var merchant = ""
    var shipPrice = 0
    var img = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityProductDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        getProductReview()
        id = intent.getIntExtra("id", 0)


        binding.buyButton.setOnClickListener {
            val bottomSheet = BottomSheet()
            val bundle = Bundle()
            bundle.putString("merchant", merchant)
            bundle.putInt("idx", id)
            bundle.putInt("shipPrice", shipPrice)
            bundle.putString("brand", binding.textView319.text.toString())
            bundle.putString("url", url)
            bundle.putString("name", binding.textView315.text.toString())


            bottomSheet.arguments = bundle
            bottomSheet.show(supportFragmentManager, bottomSheet.tag)

        }

        /** 문의하기 버튼 클릭 **/
        binding.textView304.setOnClickListener {
            val intent = Intent(this, ProductInqueryActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("brand", binding.textView319.text.toString())
            intent.putExtra("name", binding.textView315.text.toString())
            intent.putExtra("price", binding.textView320.text.toString())
            intent.putExtra("url", url)
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        /** 리뷰 이동 **/
        binding.view102.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("1", binding.progressBar1.progress)
            intent.putExtra("2", binding.progressBar2.progress)
            intent.putExtra("3", binding.progressBar3.progress)
            intent.putExtra("4", binding.progressBar4.progress)
            intent.putExtra("5", binding.progressBar5.progress)
            intent.putExtra("total", binding.textView349.text.toString())
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.textView356.setOnClickListener {
            val intent = Intent(this, ReviewActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("1", binding.progressBar1.progress)
            intent.putExtra("2", binding.progressBar2.progress)
            intent.putExtra("3", binding.progressBar3.progress)
            intent.putExtra("4", binding.progressBar4.progress)
            intent.putExtra("5", binding.progressBar5.progress)
            intent.putExtra("total", binding.textView349.text.toString())
            startActivity(intent)
            overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
        }

        binding.textView326.setOnClickListener {
            if(binding.textView325.maxLines == 1) {
                val intent = Intent(this, SungBoonActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
            }
        }

        binding.imageView71.setOnClickListener {
            if(binding.textView325.maxLines == 1) {
                val intent = Intent(this, SungBoonActivity::class.java)
                intent.putExtra("id", id)
                startActivity(intent)
                overridePendingTransition(R.xml.fade_in, R.xml.no_chagne)
            }
        }
        getDetail(id)
    }

    private fun getDetail(id : Int){
        RetrofitMansae.server.getProductDetail(id = id)
            .enqueue(object : Callback<detailProduct> {
                override fun onFailure(call: Call<detailProduct>, t: Throwable) {

                }

                @SuppressLint("SetTextI18n", "SetJavaScriptEnabled")
                override fun onResponse(call: Call<detailProduct>, response: Response<detailProduct>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            when(response.body()!!.data.favorited){
                                true -> {
                                    binding.hart.setImageResource(R.drawable.hart)
                                }
                                false -> {
                                    binding.hart.setImageResource(R.drawable.empty_hart)
                                }
                            }

                            /** 찜하기 클릭 **/
                            binding.constraintLayout9.setOnClickListener {
                                when(response.body()!!.data.favorited){
                                    true -> {
                                        Toast.makeText(this@ProductDetailActivity, "상품찜을 해제했습니다!", Toast.LENGTH_SHORT).show()
                                        binding.hart.setImageResource(R.drawable.empty_hart)
                                        favoritesToggle()
                                    }

                                    false -> {
                                        Toast.makeText(this@ProductDetailActivity, "상품을 찜했습니다!", Toast.LENGTH_SHORT).show()
                                        binding.hart.setImageResource(R.drawable.hart)
                                        favoritesToggle()
                                    }
                                }
                            }

                            merchant = response.body()?.data?.merchant?.name.toString()
                            shipPrice = response.body()?.data?.shippingPrice!!


                            url = response.body()?.data?.imageUrl.toString()
                            Glide.with(this@ProductDetailActivity).load(response.body()!!.data.imageUrl).into(binding.imageView66) // 제품 사진
                            /** 레이팅 **/


                            if(response.body()!!.data.cosSimilarity == null){
                                binding.ratingBar5.rating = 0f
                                binding.textView299.text = "0"
                            } else {
                                binding.textView299.text = "(${DecimalFormat("#.#").format(response.body()!!.data.cosSimilarity?.ratings!!)})" // 레이팅 숫자
                                binding.ratingBar5.rating = DecimalFormat("#.#").format(response.body()!!.data.cosSimilarity?.ratings!!).toFloat()
                            }



                            binding.textView315.text = response.body()!!.data.name // 제품 이름
                            binding.textView319.text = response.body()!!.data.brand // 제품 브랜드
                            if(response.body()?.data?.options?.size == 0){
                                binding.textView320.text = ""
                                binding.buyButton.isVisible = false // 옵션없는 상품은 버튼 숨김
                            } else {
                                binding.textView320.text = sliceAmountNumber(response.body()!!.data.options[0]?.price!!.toLong()) // 가격
                            }

                            if(response.body()?.data?.improvements != null){
                                for(i in response.body()!!.data.improvements){
                                    funcList.add(Func(i.name, i.imageUrl))
                                }
                            }

                            binding.recyclerView6.apply {
                                adapter = FuncAdapter(funcList)
                                if(funcList.size >= 5){
                                    layoutManager = GridLayoutManager(this@ProductDetailActivity, 5)
                                } else if(funcList.size == 0) {
                                    //layoutManager = GridLayoutManager(this@ProductDetailActivity, 1)
                                } else {
                                    layoutManager = GridLayoutManager(this@ProductDetailActivity, funcList.size)
                                }
                            }

                            /** 제형 **/
                            if(response.body()!!.data.dosageForm.toString() == "null" || response.body()!!.data.dosageForm.toString() == ""){
                                binding.textView321.isVisible = false
                                binding.textView318.isVisible = false
                            } else {
                                binding.textView321.text = response.body()!!.data.dosageForm.toString() // 제형
                            }

                            /** 성분 **/
                            var improvements = "" // 성분

                            for(i in 0 until response.body()!!.data.improvements.size){
                                improvements += "${response.body()!!.data.improvements[i].name}, "
                            }

                            if(response.body()!!.data.improvements.isEmpty()){
                                binding.textView323.isVisible = false
                                binding.textView322.isVisible = false

                            } else {
                                binding.textView323.text = improvements.substring(0, improvements.length-2) // 성분
                            }

                            /** 기능(개선사항) **/
                            var recommendedIngredients = "" // 기능(개선사항)
                            for(i in 0 until response.body()!!.data.recommendedIngredients.size){
                                recommendedIngredients += "${response.body()!!.data.recommendedIngredients[i].name}, "
                            }
                            if(response.body()!!.data.recommendedIngredients.isEmpty()){
                                binding.textView325.text = recommendedIngredients
                            } else {
//                                binding.textView325.text = recommendedIngredients.substring(0, recommendedIngredients.length-2) // 기능(개선사항)
                                binding.textView325.text = recommendedIngredients
                            }

                            /** 사용 방법 **/
                            if(response.body()!!.data.description.toString() == "null" || response.body()!!.data.description.toString() == ""){
                                binding.textView328.isVisible = false
                                binding.textView327.isVisible = false
                            } else {
                                binding.textView328.text = response.body()!!.data.description.toString() // 사용방법
                            }

                            /** rating **/
                            binding.textView349.text = DecimalFormat("#.#").format(response.body()!!.data.rating).toString() // total rating text
                            binding.ratingBar8.rating = response.body()!!.data.rating.toFloat() // total rating

                            binding.progressBar5.progress = response.body()!!.data.ratingCountMap.`5`
                            binding.textView355.text = response.body()!!.data.ratingCountMap.`5`.toString()
                            binding.progressBar4.progress = response.body()!!.data.ratingCountMap.`4`
                            binding.progressBar3.progress = response.body()!!.data.ratingCountMap.`3`
                            binding.progressBar2.progress = response.body()!!.data.ratingCountMap.`2`
                            binding.progressBar1.progress = response.body()!!.data.ratingCountMap.`1`

                            /** test webview **/
                            binding.webview.settings.javaScriptEnabled = true
                            binding.webview.settings.useWideViewPort = true
                            binding.webview.settings.loadWithOverviewMode = true
                            binding.webview.settings.useWideViewPort = true
                            binding.webview.settings.builtInZoomControls = true
                            binding.webview.settings.setSupportZoom(true)
                            binding.webview.settings.displayZoomControls = false

                            val str = response.body()?.data?.details
                            binding.webview.loadData(str?.replace("860", "1280").toString(), "text/html", "UTF-8")
                        }
                        else -> {
                            Toast.makeText(this@ProductDetailActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    fun getWordCount(){
        RetrofitMansae.server.getReviewWord()
            .enqueue(object : Callback<wordCount>{
                override fun onFailure(call: Call<wordCount>, t: Throwable) {

                }

                override fun onResponse(call: Call<wordCount>, response: Response<wordCount>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            for(i in response.body()!!.data){
                                wordList.add(i.name)
                            }
                            val reviewWordCheck = Array(wordList.size) { 0 }
                            val reviewWordCheck2 : ArrayList<countModel> = arrayListOf()
                            val reviewWordCheckSort: List<countModel>
                            val newreviewWordList : List<countModel>
                            var shufled : List<countModel> = arrayListOf()
                            if(newReviewList.isNotEmpty()){

                                for(i in 0 until newReviewList.size) {
                                    for (j in 0 until wordList.size) {
                                        if (newReviewList[i].content.contains(wordList[j])) {
                                            reviewWordCheck[j]++
                                        }
                                    }
                                }
                                for(i in 0 until wordList.size){
                                    reviewWordCheck2.add(
                                            countModel(
                                                    word = wordList[i], count = reviewWordCheck[i]
                                            )
                                    )
                                }


                                reviewWordCheckSort = reviewWordCheck2.sortedByDescending {
                                    it.count
                                }
                                reviewWordCheckSort[0].rankFrist = true
                                reviewWordCheckSort[1].rankSecondThird = true
                                reviewWordCheckSort[2].rankSecondThird = true
                               // var newreviewWordList : List<countModel> = arrayListOf()
                                newreviewWordList = if (reviewWordCheckSort.count() <= 8){
                                    reviewWordCheckSort.toMutableList()
                                } else {
                                    arrayListOf(reviewWordCheckSort[0],reviewWordCheckSort[1],reviewWordCheckSort[2],reviewWordCheckSort[3],reviewWordCheckSort[4],reviewWordCheckSort[5],reviewWordCheckSort[6],reviewWordCheckSort[7])
                                }
                                 shufled = newreviewWordList.shuffled()
                            }

                            binding.recyclerView7.apply {
                                layoutManager = GridLayoutManager(this@ProductDetailActivity, 4)
                                adapter = WordReviewAdapter(shufled)
                            }
                        }
                    }
                }
            })
    }

    fun favoritesToggle(){
        RetrofitMansae.server.favorites(id = id)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {

                    }
                })
    }

    private fun getProductReview(){
        RetrofitMansae.server.getReView(id = intent.getIntExtra("id", 0), pageSize = 50)
            .enqueue(object : Callback<review>{
                override fun onFailure(call: Call<review>, t: Throwable) {

                }

                override fun onResponse(call: Call<review>, response: Response<review>) {
                    when(response.body()?.responseCode){
                        "SUCCESS" -> {
                            binding.reviewCount.text = response.body()!!.totalCount.toString()
                            /** 리뷰 첫번째꺼만 **/

                            if(response.body()?.data?.isNotEmpty()!!){
                                for(i in response.body()!!.data){
                                    newReviewList.add(
                                        ReviewModel(
                                            content = i.content.toString(),
                                            imageUrl = i.imageUrl.toString(),
                                            rating = i.rating,
                                            nickname = i.userData.nickname,
                                            skinProblems = i.userData.skinProblems as ArrayList<String>,
                                            imageUrls = i.imageUrls as ArrayList<String>,
                                            createdDate = i.createdDate,
                                            birthDate = i.userData.birthDate
                                        )
                                    )
                                }

                                reviewList.add(ReviewModel(
                                    content = response.body()!!.data[0].content.toString(),
                                    imageUrl = response.body()!!.data[0].userData.imageUrl,
                                    rating = response.body()!!.data[0].rating,
                                    nickname = response.body()!!.data[0].userData.nickname,
                                    skinProblems = response.body()!!.data[0].userData.skinProblems as ArrayList<String>,
                                    imageUrls = response.body()!!.data[0].imageUrls as ArrayList<String>,
                                    createdDate =  response.body()!!.data[0].createdDate,
                                    birthDate = response.body()!!.data[0].userData.birthDate
                                ))
                                val reviewAdapter = ReviewAdapter(reviewList)
                                binding.recyclerView2.apply {
                                    adapter = reviewAdapter
                                    layoutManager = LinearLayoutManager(this@ProductDetailActivity)
                                }

//                                reviewAdapter.setItemClickListner(object : ReviewAdapter.ItemClickListener{
//                                    override fun onClick(view: View, position: Int) {
//                                        val intent = Intent(this@ProductDetailActivity, MyFaceActivity::class.java)
//                                        intent.putExtra("img", reviewList[0].imageUrls[position].toString())
//                                        startActivity(intent)
//                                        overridePendingTransition(R.xml.slide_left, R.xml.no_chagne)
//                                    }
//                                })
                                getWordCount()
                            } else {
                                binding.view94.isVisible = false
                            }

                        }

                        else -> {
                            Toast.makeText(this@ProductDetailActivity, "서버에러", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, R.xml.slide_right)
    }

    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)+"원"
    }
}