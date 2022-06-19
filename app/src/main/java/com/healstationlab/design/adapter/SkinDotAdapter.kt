package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.healstationlab.design.R
import com.healstationlab.design.databinding.SkinDotItemBinding
import com.healstationlab.design.model.DotModel

class SkinDotAdapter(private val dotList : ArrayList<DotModel>) : RecyclerView.Adapter<SkinDotAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = SkinDotItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return dotList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(dotList[position])
    }

    inner class Holder(binding: SkinDotItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val dot = binding.dot
        val title = binding.title
        val result = binding.result
        val content = binding.content

        @SuppressLint("SetTextI18n")
        fun bind(binding : DotModel){
            when(binding.result){
                "모공" -> {
                    when(binding.degree){
                        2 -> {content.text = "섬세한 피부이나 몇 개의 확장된 모공이 발견됩니다"}
                        3 -> {content.text = "모공이 보이거나 모공보다 작은 크기의 각질이 있습니다"}
                        4 -> {content.text = "모공과 동일한 크기의 산화된 묵은각질이 모공을 막고있습니다."}
                    }
                    dot.setBackgroundResource(R.drawable.green_dot)
                    title.text = "증증정도 모공"
                }

                "블랙헤드" -> {
                    when(binding.degree){
                        2 -> {content.text = "희미하게 보이는 여러 개의 블랙헤드"}
                        3 -> {content.text = "뚜렷한 검은 색 돌출부가 있습니다"}
                        4 -> {content.text = "뚜렷한 검은 색 돌출부가 있으며, 모공이 확대되어 돌출된 동공을 형성하고 있습니다"}
                    }
                    dot.setBackgroundResource(R.drawable.red_dot)
                    title.text = "증증정도 블랙헤드"
                }

                "주름" -> {
                    when(binding.degree){
                        2 -> {content.text = "얼굴이 무표정일 때 희미하게 미세 주름이 보입니다"}
                        3 -> {content.text = "얼굴이 무표정일 때 주름이 뚜렷하게 보이며 힘을 가해 스트레칭한 후에는 주름이 크게 줄어듭니다"}
                        4 -> {content.text = "눈에 띄는 주름이 있으며 피부를 당긴후에도 여전히 잘 보입니다"}
                    }
                    dot.setBackgroundResource(R.drawable.dark_blue_dot)
                    title.text = "증증정도 주름"
                }

                "색조반점" -> {
                    when(binding.degree){
                        2 -> {content.text = "희미하게 보이는 1-2곳의 면적이 작고 분산되어 있는 색소침착"}
                        3 -> {content.text = "뚜렷한 색소 침착과 스팟이 있으며 집중된 영역이 있습니다"}
                        4 -> {content.text = "뚜렷하게 어두운 톤의 스팟이 있으며, 집중된 영역이 있고 범위 또한 비교적 넓습니다"}
                    }
                    dot.setBackgroundResource(R.drawable.purple_dot)
                    title.text = "중증정도 색조반점"
                }

                "여드름" -> {
                    when(binding.degree){
                        2 -> {content.text = "여드름, 중간 등급 수준의 구진 및 농포가 있으며, 총 병소 수는 31-50개입니다"}
                        3 -> {content.text = "대량의 구진과 농포가 있으며, 큰 염증성 결손이 간간히 보입니다. 총 병소 수는 51-100개이며 결절은 3개 미만입니다."}
                        4 -> {content.text = "결절/낭종성 여드름 또는 응집성 여드름, 총 병소 수가 100개를 초과하며, 결절/낭종이 3개를 초과합니다"}
                    }
                    dot.setBackgroundResource(R.drawable.yellow_dot)
                    title.text = "중증정도 여드름"
                }

                "민감성 피부" -> {
                    when(binding.degree){
                        1 -> {content.text = "얼굴에 눈에 보이는 핏줄기가 없으며 외부 자극에 대한 피부의 적응 능력이 강합니다"}
                        2 -> {content.text = "육안으로 숨겨진 핏줄기가 있으며 각질층이 얇습니다"}
                        3 -> {content.text = "육안으로 뚜렷하게 확인되는 뭉친 홍반이 있으며, 모세혈관이 확장되어 있습니다"}
                    }
                    dot.setBackgroundResource(R.drawable.crimson_dot)
                    title.text = "중증정도 민감성"

                }

                "다크서클" -> {
                    when(binding.degree){
                        2 -> {content.text = "눈꺼풀 아래 피부에 가벼운 색소 침착 또는 정맥 혈관이 있습니다"}
                        3 -> {content.text = "눈꺼풀 아래 피부에 색소 침착 및 정맥 혈관이 보입니다"}
                        4 -> {content.text = "눈꺼풀 아래 피부에 눈에 띄는 색소 침착과 정맥 혈관이 있으며 눈두덩이가 두툼하기도 합니다"}
                    }
                    dot.setBackgroundResource(R.drawable.blue_dot)
                    title.text = "중증정도 다크서클"
                }
            }
        }
    }
}