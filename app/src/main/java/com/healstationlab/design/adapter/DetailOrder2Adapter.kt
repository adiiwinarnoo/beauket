package com.healstationlab.design.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.healstationlab.design.databinding.DetailOrder2ItemBinding
import com.healstationlab.design.model.myOrderModel
import java.text.DecimalFormat

class DetailOrder2Adapter(private val testList : ArrayList<myOrderModel>) : RecyclerView.Adapter<DetailOrder2Adapter.Holder>() {
    private lateinit var itemClickListener: ItemClickListener

    interface ItemClickListener {
        fun onCart(view: View, position: Int)
        fun onLeft(view : View, text : String, position: Int)
        fun onRight(view : View, text : String, position: Int)
        fun onReview(view : View, position: Int)
        fun onClick(view : View, position: Int)
        fun onCenter(view : View, text : String, position: Int)
    }

    fun setItemClickListner(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding : DetailOrder2ItemBinding = DetailOrder2ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return testList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(testList[position])

        /** 왼쪽 **/
        holder.textView230.setOnClickListener {
            itemClickListener.onLeft(it, holder.textView230.text.toString(), position)
        }

        /** 오른쪽 **/
        holder.textView397.setOnClickListener {
            itemClickListener.onRight(it, holder.textView397.text.toString(), position)
        }

        /** 가운데 긴거 **/
        holder.textView415.setOnClickListener {
            itemClickListener.onCenter(it, holder.textView415.text.toString(), position)
        }

        /** 리뷰 **/
        holder.textView398.setOnClickListener {
            itemClickListener.onReview(it, position)
        }

        /** 장바구니 **/
        holder.textView395.setOnClickListener {
            itemClickListener.onCart(it, position)
        }


        /** 상품으로 이동 **/
        holder.imageView106.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.textView232.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        holder.textView233.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    inner class Holder(binding : DetailOrder2ItemBinding) : RecyclerView.ViewHolder(binding.root){

        private val textView172 = binding.textView172 // 입점사
        private val textView229 = binding.textView229 // 상태
        val imageView106 = binding.imageView106 // img

        val textView232 = binding.textView232 // brand
        val textView233 = binding.textView233 // name
        private val textView281 = binding.textView281 // money
        private val textView394 = binding.textView394 // 개수

        val textView230 = binding.textView230 // 주문취소
        val textView397 = binding.textView397 // 배송조회
        val textView398 = binding.textView398 // 구매후기

        val textView395 = binding.textView395 // 장바구니 담기

        val textView415 = binding.textView415 // 배송조회, 주문취소 긴거


        @SuppressLint("SetTextI18n")
        fun bind(binding : myOrderModel){
            Glide.with(itemView).load((binding.imageUrl)).into(imageView106) // 제품 이미지
            textView172.text = binding.merchantName // 입점사
            when(binding.status){
                "ACCEPT" -> {
                    textView229.text = "접수"
                    textView415.text = "주문취소"

                    textView230.isVisible = false
                    textView397.isVisible = false
                    textView398.isVisible = false
                    textView415.isVisible = false
                }
                "SHIPPING_READY" -> {
                    textView229.text = "배송준비중"
                    textView415.text = "배송조회"

                    textView415.isVisible = true
                    textView230.isInvisible = false
                    textView398.isVisible = false
                    textView397.isInvisible = false
                }
                "SHIPPING" -> {
                    textView229.text = "배송준비중"
                    textView415.text = "배송조회"

                    textView415.isVisible = true
                    textView230.isInvisible = false
                    textView398.isVisible = false
                    textView397.isInvisible = false
                }
                "SHIPPED" -> {
                    textView229.text = "배송완료"
                    textView230.text = "교환/반품"
                    textView397.text = "배송조회"
                    textView398.isVisible = true
                    textView415.isVisible = false
                }
                "REFUND" -> {
                    textView229.text = "교환/반품요청"
                    textView230.isVisible = false
                    textView397.isVisible = false
                    textView398.isVisible = false
                    textView415.isVisible = false
                }
                "CANCELLED" -> {
                    textView229.text = "주문취소"
                    textView230.isVisible = false
                    textView397.isVisible = false
                    textView398.isVisible = false
                    textView415.isVisible = false
                }
            }
            textView232.text = binding.brand // 브랜드 이름
            textView233.text = binding.name // 상품이름
            textView281.text = sliceAmountNumber(binding.totalPrice.toLong())+"원" // 가격
            textView394.text = binding.amount.toString()+"개" // 수량

        }
    }
    fun sliceAmountNumber(number : Long) : String {
        val decimalFormat = DecimalFormat("###,###")
        return decimalFormat.format(number)
    }
}