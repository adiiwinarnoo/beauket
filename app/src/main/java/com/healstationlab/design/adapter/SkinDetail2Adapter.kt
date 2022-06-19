package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.DetailItemBinding
import com.healstationlab.design.databinding.SkinDetail2ItemBinding
import com.healstationlab.design.dto.*
import com.healstationlab.design.model.detailModel

class SkinDetail2Adapter(private val skinDetail2ArrayList : ArrayList<Any>, val context : Context, val pore : Int, val blackRimOfEye : Int, val blackhead : Int, val acne : Int, val wrinkle : Int, val speckle : Int, val sensitive : Int) : RecyclerView.Adapter<SkinDetail2Adapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = SkinDetail2ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return skinDetail2ArrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.detailbutton.setOnClickListener {
            if(holder.detailbutton.isFocusable){
                holder.textView174.text = "상황 개선 접기"
                holder.constraintLayout.isVisible = true
                holder.detailbutton.isFocusable = false
                holder.detailimg.setBackgroundResource(R.drawable.arrow_up)
            } else {
                holder.textView174.text = "상황 개선 자세히 보기"
                holder.constraintLayout.isVisible = false
                holder.detailbutton.isFocusable = true
                holder.detailimg.setBackgroundResource(R.drawable.down_arrow)
            }
        }

        when(position){
            0 -> holder.poreBind(skinDetail2ArrayList[0] as reportMeitu.Data.Pore)
            1 -> holder.blackHeadBind(skinDetail2ArrayList[1] as reportMeitu.Data.Blackhead)
            2 -> holder.speckleBind(skinDetail2ArrayList[2] as reportMeitu.Data.Speckle)
            3 -> holder.wrinkleBind(skinDetail2ArrayList[3] as reportMeitu.Data.Wrinkle)
            4 -> holder.acneBind(skinDetail2ArrayList[4] as reportMeitu.Data.Acne)
            5 -> holder.sensitiveBind(skinDetail2ArrayList[5] as reportMeitu.Data.Sensitive)
            6 -> holder.blackRimBind(skinDetail2ArrayList[6] as reportMeitu.Data.BlackRimOfEye)
        }
    }

    inner class Holder(binding : SkinDetail2ItemBinding) : RecyclerView.ViewHolder(binding.root) {
        /** Top **/
        var textView174 = binding.textView174

        private var toptitletext = binding.topTitleText
        private var titletext = binding.titleText
        private var titleper = binding.titlePer
        var progressBar = binding.progressBar

        /** Center **/
        private val verticalview = binding.verticalView
        private val toplefttextview = binding.topLeftTextview
        private val topcentertextview = binding.topCenterTextview
        private val righttoptextview = binding.rightTopTextview
        private val bottonlefttextview = binding.bottonLeftTextview
        private val bottoncentertextview = binding.bottonCenterTextview
        private val rightbottontextview = binding.rightBottonTextview

        /** Button **/
        val detailbutton = binding.detailButton
        private val detailtextview = binding.detailText
        val constraintLayout = binding.back
        val detailimg = binding.detailImg

        /** 모공 **/
        @SuppressLint("SetTextI18n")
        fun poreBind(binding : reportMeitu.Data.Pore){
            visibleFalseCenter("모공","중증 정도 모공 : ", "면적비율", "청결도")
            titleper.text = "$pore%" // user avg?
            progressBar.progress = 100 - pore

//            when(binding.score){
//                in 90..100 -> detail_textview.text = context.resources.getString(R.string.pore_top)
//                in 50..89 -> detail_textview.text = context.resources.getString(R.string.pore_mid)
//                else -> detail_textview.text = context.resources.getString(R.string.pore_bottom)
//            }

            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_pore)),
                detailModel(context.resources.getString(R.string.new_pore2)),
                detailModel(context.resources.getString(R.string.new_pore3))
            )

            when(binding.type){
                1 -> {
                    bottonlefttextview.text = percenterToString(binding.percentage) + "% 작음"
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                        layoutManager = LinearLayoutManager(context)
                    }

                }
                2 -> {
                    bottonlefttextview.text = percenterToString(binding.percentage) + "% 조금 큰 편"
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                3 -> {
                    bottonlefttextview.text = percenterToString(binding.percentage) + "% 큰 편"
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }
            when(binding.cleanliness_type){
                1 -> rightbottontextview.text = "좋음"
                2 -> rightbottontextview.text = "나쁨"
                3 -> rightbottontextview.text = "매우나쁨"
                4 -> rightbottontextview.text = "보통"
            }
        }

        /** 블랙헤드 **/
        @SuppressLint("SetTextI18n")
        fun blackHeadBind(binding : reportMeitu.Data.Blackhead){
            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_bleak)),
                detailModel(context.resources.getString(R.string.new_bleak2)),
                detailModel(context.resources.getString(R.string.new_bleak3)),
                detailModel(context.resources.getString(R.string.new_bleak4))
            )

            when(binding.degree){
                1 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                2 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                3 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                4 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[3]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            progressBar.progress = 100 - blackhead
            visibleFalseTopBottom("블랙헤드","중증 정도 블랙헤드", "밀집정도")
            titleper.text = "$blackhead%" // user avg?
            bottoncentertextview.text = percenterToString(binding.percentage) + "% 밀집"
        }

        /** 색소스팟 **/
        @SuppressLint("SetTextI18n")
        fun speckleBind(binding : reportMeitu.Data.Speckle) {

            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_sac)),
                detailModel(context.resources.getString(R.string.new_sac2)),
                detailModel(context.resources.getString(R.string.new_sac3)),
                detailModel(context.resources.getString(R.string.new_sac4))
            )

            when(binding.degree){
               1 -> {
                   detailtextview.apply {
                       adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                       layoutManager = LinearLayoutManager(context)
                   }
               }

                2 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                3 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                4 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[3]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

            }

            val max = binding.t[0].quantity + binding.t[1].quantity + binding.t[2].quantity
            var area = ""

            if(max < binding.cheek[0].quantity + binding.cheek[1].quantity + binding.cheek[2].quantity){
                area = "뺨"
            }

            progressBar.progress = 100 - speckle
            visibleFalseCenter("색소스팟", "중증 정도 색소 스팟 : ", "면접 점유 비율", "문제 집중 영역")
            titleper.text = "$speckle%" // user avg?
            bottonlefttextview.text = percenterToString(binding.sum.percentage) + "% 부위"
            rightbottontextview.text = when(area){
                "" -> "T존"
                else -> "뺨"
            }
        }

        /** 주름 **/
        @SuppressLint("SetTextI18n")
        fun wrinkleBind(binding : reportMeitu.Data.Wrinkle){
            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_joo)),
                detailModel(context.resources.getString(R.string.new_joo2)),
                detailModel(context.resources.getString(R.string.new_joo3)),
                detailModel(context.resources.getString(R.string.new_joo4))
            )

            detailtextview.apply {
                adapter = DetailTextAdapter(poreList)
                layoutManager = LinearLayoutManager(context)
            }

            when(binding.degree){
                1 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                2 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                3 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                4 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[3]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            progressBar.progress = 100 - wrinkle
            var trouble = ""
            for(i in binding.trouble_list){
                when(i.detect){
                    1 -> {
                        when(i.type){
                            1 -> trouble += "눈 초리 주름, "
                            2 -> trouble += "눈밑 주름, "
                            4 -> trouble += "눈밑 잔주름, "
                            8 -> trouble += "이마 주름, "
                            16 -> trouble += "팔자 주름, "
                            32 -> trouble += "이마 잔주름, "
                        }
                    }
                }
            }
            visibleFalseTopBottom("주름","중증 정도 주름", "주름종류")
            titleper.text = "$wrinkle%" // user avg?
            bottoncentertextview.text = trouble.substring(0, trouble.length-2)
        }

        /** 여드름 **/
        @SuppressLint("SetTextI18n")
        fun acneBind(binding : reportMeitu.Data.Acne){
            progressBar.progress = 100 - acne

            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_yu)),
                detailModel(context.resources.getString(R.string.new_yu2)),
                detailModel(context.resources.getString(R.string.new_yu3)),
                detailModel(context.resources.getString(R.string.new_yu4)),
                detailModel(context.resources.getString(R.string.new_yu5))
            )

            when(binding.level){
                1 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                2 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                3 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                4 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[3]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                5 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[4]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            var acen = binding.t[0].quantity
            var acenResult = ""

            if(acen < binding.cheek[0].quantity){
                acen = binding.cheek[0].quantity
                acenResult = "뺨"
            }
            if(acen < binding.chin[0].quantity){ acenResult = "턱" }

            visibleFalseCenter("여드름", "1급 여드름", "여드름 수", "문제 집중 영역")
            titleper.text = "$acne%" // user avg?
            bottonlefttextview.text = binding.sum.quantity.toString()
            rightbottontextview.text = when(acenResult){
                "뺨" -> "뺨"
                "턱" -> "턱"
                else -> "T존"
            }
        }

        /** 민감성피부 **/
        @SuppressLint("SetTextI18n")
        fun sensitiveBind(binding : reportMeitu.Data.Sensitive){
            progressBar.progress = 100 - sensitive
            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_min)),
                detailModel(context.resources.getString(R.string.new_min2)),
                detailModel(context.resources.getString(R.string.new_min3)),
                detailModel(context.resources.getString(R.string.new_min4))
            )

            when(binding.degree){
                1 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                2 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                3 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                4 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[3]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            visibleFalseCenter("민감성 피부", "경도 민감도", "면접 점유 비율", "피부유형")
            titleper.text = "$sensitive%" // user avg?
            bottonlefttextview.text = percenterToString(binding.percentage) + "%"
            rightbottontextview.text = when(binding.type){
                1 -> "강한 내성 피부"
                2 -> "정상 피부"
                3 -> "약한 민감성 피부"
                else -> "심각한 민감성 피부"
            }
        }

        /** 다크서클 @@@@@ 테스트 @@@@@**/
        @SuppressLint("SetTextI18n")
        fun blackRimBind(binding : reportMeitu.Data.BlackRimOfEye){
            progressBar.progress = 100 - blackRimOfEye
            val poreList : ArrayList<detailModel> = arrayListOf(
                detailModel(context.resources.getString(R.string.new_dark)),
                detailModel(context.resources.getString(R.string.new_dark2)),
                detailModel(context.resources.getString(R.string.new_dark3)),
                detailModel(context.resources.getString(R.string.new_dark4))
            )

            when(binding.degree){
                1 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[0]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                2 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[1]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }

                3 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[2]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                4 -> {
                    detailtextview.apply {
                        adapter = DetailTextAdapter(arrayListOf(poreList[3]))
                        layoutManager = LinearLayoutManager(context)
                    }
                }
            }

            visibleFalseTopBottom("다크서클", "다크서클", "점수")
            titleper.text = "$blackRimOfEye%" // user avg?
            bottoncentertextview.text = binding.score.toString()+"점"
//            right_botton_textview.text = when(binding.degree){
//                1 -> "없음"
//                2 -> "약함"
//                3 -> "보통"
//                else -> "심각"
//            }
        }

        private fun visibleFalseCenter(top_title_text : String, title_text : String, top_left_textview : String, right_top_textview : String){
            titleper.text = ""
            progressBar.isEnabled = false
            topcentertextview.isVisible = false
            bottoncentertextview.isVisible = false
            this.titletext.text = title_text
            this.toplefttextview.text = top_left_textview
            this.righttoptextview.text = right_top_textview
            this.toptitletext.text = top_title_text
        }

        private fun visibleFalseTopBottom(top_title_text : String, title_text : String, top_center_textview : String){
            titleper.text = ""
            this.titletext.text = title_text
            this.topcentertextview.text = top_center_textview
            progressBar.isEnabled = false
            verticalview.isVisible = false
            toplefttextview.isVisible = false
            righttoptextview.isVisible = false
            bottonlefttextview.isVisible = false
            rightbottontextview.isVisible = false
            this.toptitletext.text = top_title_text
        }

        private fun percenterToString(per : Double) : String{
            val perString = per.toString()

            return if(perString.toCharArray()[2] == '0'){
                if(perString == "0.0"){
                    "0"
                } else {
                    perString.substring(3, 4)
                }
            }  else {
                perString.substring(2, 4)
            }
        }
    }
}

class DetailTextAdapter(val list : ArrayList<detailModel>) : RecyclerView.Adapter<DetailTextAdapter.DetailHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailHolder {
        val holder = DetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailHolder(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DetailHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class DetailHolder(binding : DetailItemBinding) : RecyclerView.ViewHolder(binding.root){
//        val title = binding.title
        val contents = binding.contents

        fun bind(binding : detailModel){
//            title.text = binding.title
            contents.text = binding.contents
        }
    }
}