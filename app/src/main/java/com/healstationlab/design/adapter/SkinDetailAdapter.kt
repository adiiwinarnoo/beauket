package com.healstationlab.design.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.SkinDetailItemBinding
import com.healstationlab.design.dto.reportMeitu

class SkinDetailAdapter(private val skinDetailArrayList : ArrayList<Any>, val context : Activity) : RecyclerView.Adapter<SkinDetailAdapter.Holder>() {

    companion object {
        var width = 0
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = SkinDetailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return skinDetailArrayList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.score.post {
            width = holder.score.width
            when(position){
                0 -> {
                    holder.bindSlsd(skinDetailArrayList[0] as reportMeitu.Data.Preview.Slsd)
                }
                1 -> holder.bindPfpz(skinDetailArrayList[1] as reportMeitu.Data.Preview.Pfpz)
                2 -> holder.bindHssdx(skinDetailArrayList[2] as reportMeitu.Data.Preview.Hssdx)
            }
        }

        holder.detailbutton.setOnClickListener {
            if(holder.detailbutton.isFocusable){
                holder.textView166.text = "결과 상세 접기"
                holder.detailtextview.isVisible = true
                holder.detailimg.setBackgroundResource(R.drawable.arrow_up)
                holder.detailbutton.isFocusable = false
            } else {
                holder.textView166.text = "결과 상세 보기"
                holder.detailtextview.isVisible = false
                holder.detailimg.setBackgroundResource(R.drawable.down_arrow)
                holder.detailbutton.isFocusable = true
            }
        }
    }

    inner class Holder(binding : SkinDetailItemBinding) : RecyclerView.ViewHolder(binding.root){
        val level = binding.level
        // val level2 = binding.textView389

        val textView166 =  binding.textView166

        val score = binding.score // SeekBar

        private val title1 = binding.title1
        private val title2 = binding.title2
        private val title3 = binding.title3

        private val score1 = binding.score1
        private val score2 = binding.score2
        private val score3 = binding.score3

        private val sublevel1 = binding.subLevel1
        private val sublevel2 = binding.subLevel2
        private val sublevel3 = binding.subLevel3

        val detailimg = binding.detailImg

        val detailbutton = binding.detailButton
        val detailtextview = binding.detailTextview

        private val skinbackground = binding.skinBackgorund

        private val total = binding.total


        /** 피부 노화 **/
        fun bindSlsd(test : reportMeitu.Data.Preview.Slsd){
            when(test.level){
                1 -> {
                    level.text = "노화속도"
                    // level2.text = "느림" // 감속
                    detailtextview.text = context.resources.getString(R.string.slsd1)
                }
                2 -> {
                    level.text = "노화속도"
                    // level2.text = "정상" // 정상
                    detailtextview.text = context.resources.getString(R.string.slsd2)
                }
                3 -> {
                    level.text = "노화속도"
                    // level2.text = "빠름" // 가속
                    detailtextview.text = context.resources.getString(R.string.slsd3)
                }
            }

            skinbackground.setImageResource(R.drawable.nohwa)

            total.text = test.score.toString()
            total.x = (width * "0.${test.score}".toFloat()+15)

            score.progress = test.score
            score.isEnabled = false

            title1.text = "황산화"
            title2.text = "황당화"
            title3.text = "황노화"

            score1.text = test.sub_item.kyhl.score.toString()
            score2.text = test.sub_item.kthl.score.toString()
            score3.text = test.sub_item.kglhl.score.toString()

            when(test.sub_item.kyhl.level){
                1 -> sublevel1.text = "강함"
                2 -> sublevel1.text = "정상"
                3 -> sublevel1.text = "중간"
                4 -> sublevel1.text = "약함"
            }

            when(test.sub_item.kthl.level){
                1 -> sublevel2.text = "강함"
                2 -> sublevel2.text = "정상"
                3 -> sublevel2.text = "중간"
                4 -> sublevel2.text = "약함"
            }

            when(test.sub_item.kglhl.level){
                1 -> sublevel3.text = "강함"
                2 -> sublevel3.text = "정상"
                3 -> sublevel3.text = "중간"
                4 -> sublevel3.text = "약함"
            }
        }

        /**피부 장벽 **/
        fun bindPfpz(test : reportMeitu.Data.Preview.Pfpz){
            total.text = test.score.toString()
            total.x = (width * "0.${test.score}".toFloat()+15)
            score.isEnabled = false
            score.progress = test.score

            when(test.level){
                1 -> {
                    level.text = "피부장벽"
                    //level2.text = "건강함"
                    detailtextview.text = context.resources.getString(R.string.pfpz1)
                }
                2 -> {
                    level.text = "피부장벽"
                    //level2.text = "약한 손상"
                    detailtextview.text = context.resources.getString(R.string.pfpz2)
                }
                3 -> {
                    level.text = "피부장벽"
                    //level2.text = "심한 손상"
                    detailtextview.text = context.resources.getString(R.string.pfpz3)
                }
            }


            skinbackground.setImageResource(R.drawable.pibu)

            title1.text = "보습력"
            title2.text = "항균력"
            title3.text = "항염능"

            score1.text = test.sub_item.ssl.score.toString()
            score2.text = test.sub_item.kjl.score.toString()
            score3.text = test.sub_item.kyl.score.toString()

            when(test.sub_item.ssl.level){
                1 -> sublevel1.text = "강함"
                2 -> sublevel1.text = "정상"
                3 -> sublevel1.text = "중간"
                4 -> sublevel1.text = "약함"
            }

            when(test.sub_item.kjl.level){
                1 -> sublevel2.text = "강함"
                2 -> sublevel2.text = "정상"
                3 -> sublevel2.text = "중간"
                4 -> sublevel2.text = "약함"
            }

            when(test.sub_item.kyl.level){
                1 -> sublevel3.text = "강함"
                2 -> sublevel3.text = "정상"
                3 -> sublevel3.text = "중간"
                4 -> sublevel3.text = "약함"
            }
        }

        fun bindHssdx(test : reportMeitu.Data.Preview.Hssdx){
            score3.isVisible = false
            sublevel3.isVisible = false
            title3.isVisible = false
            total.text = test.score.toString()
            total.x = (width * "0.${test.score}".toFloat()+15)

            score.isEnabled = false
            score.progress = test.score
            skinbackground.setImageResource(R.drawable.melanin)

            when(test.level){
                1 -> {
                    level.text = "멜라닌대사"
                    //level2.text = "건강함"
                    detailtextview.text = context.resources.getString(R.string.hssdx1)
                }
                2 -> {
                    level.text = "멜라닌대사"
                    //level2.text = "약한 손상"
                    detailtextview.text = context.resources.getString(R.string.hssdx2)
                }
                3 -> {
                    level.text = "멜라닌대사"
                    //level2.text = "심한 손상"
                    detailtextview.text = context.resources.getString(R.string.hssdx3)
                }
            }


            title1.text = "멜라닌 생성"
            title2.text = "색소침착"

            score1.text = test.sub_item.ybl.score.toString()
            score2.text = test.sub_item.khl.score.toString()

            when(test.sub_item.ybl.level){
                1 -> sublevel1.text = "강함"
                2 -> sublevel1.text = "정상"
                3 -> sublevel1.text = "중간"
                4 -> sublevel1.text = "약함"
            }

            when(test.sub_item.khl.level){
                1 -> sublevel2.text = "강함"
                2 -> sublevel2.text = "정상"
                3 -> sublevel2.text = "중간"
                4 -> sublevel2.text = "약함"
            }
        }
    }
}