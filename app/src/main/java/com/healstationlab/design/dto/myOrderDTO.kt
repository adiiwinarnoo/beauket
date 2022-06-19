package com.healstationlab.design.dto

data class myOrderDTO(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>,
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int
) {
    data class Data(
        val User: User2,
        val ProductOrderMeta: List<ProductOrderMeta2>) {
        data class User2(
            val id: Int,
            val loginId: String,
            val thirdPartyId: Any,
            val name: String,
            val nickname: String,
            val email: Any,
            val contact: String,
            val imageUrl: String,
            val level: Int,
            val points: Int,
            val gender: String,
            val username: String,
            val skinType: String,
            val analysisData: Boolean,
            val married: Boolean,
            val hasChildren: Boolean,
            val cosmeticProcedure: Boolean,
            val sensitiveConstitution: Boolean,
            val agreePrivacyPolicy: Boolean,
            val agreeTermsOfUse: Boolean,
            val agreeSms: Boolean,
            val agreeEmail: Boolean,
            val alarmOn: Boolean,
            val admin: Boolean,
            val alarmEventOn: Boolean,
            val alarmNoticeOn: Boolean,
            val alarmOrderStatusOn: Boolean,
            val alarmProductInquiryOn: Boolean,
            val alarmInfoOn: Boolean,
            val skinProblems: List<String>,
            val authType: String,
            val recommendProductCode: String,
            val serviceAgreeAt: List<Int>,
            val privacyAgreeAt: List<Int>,
            val newPassword: Any,
            val genderStr: Any,
            val birthDateParam: Any,
            val enabled: Boolean,
            val authorities: Any,
            val accountNonLocked: Boolean,
            val credentialsNonExpired: Boolean,
            val accountNonExpired: Boolean,
            val birthDate: String
        )

        data class ProductOrderMeta2(
            val id: Int,
            val pgTid : String?,
            val receiverName: String,
            val address: String,
            val addressDetail: String,
            val zoneCode: String,
            val contact: String,
            val invoiceNumber: Any,
            val deliveryInstruction: String,
            val orderNo: Any,
            val paymentType: String,
            val status: String,
            val orderItems: List<OrderItem>,
            val usePoint: Int,
            val amount: Int,
            val totalPrice: Int,
            val couponDiscountPrice: Int,
            val createdAt : ArrayList<Int>,
            val createdDate : String
        ) {
            data class OrderItem(
                val id: Int,
                val receiverName: String,
                val address: String,
                val addressDetail: String,
                val zoneCode: String,
                val contact: String,
                val invoiceNumber: Any,
                val deliveryInstruction: String,
                val orderNo: String,
                val status: String,
                val productOption: ProductOption,
                val product: Product,
                val amount: Int,
                val deliveryCount : Boolean,
                val deliveryFee : Long,
                val paymentFee : Long,
                val totalPrice: Int,
                val couponDiscountPrice: Int,
                val invoiceUrl: String?
            ) {
                data class ProductOption(
                    val id: Int,
                    val name: String,
                    val price: Int,
                    val originalPrice: Int,
                    val stocks: Int
                )

                data class Product(
                    val id: Int,
                    val merchant: Merchant,
                    val name: String,
                    val brand: String,
                    val createdAt : ArrayList<Int>,
                    val updatedAt : ArrayList<Int>,
                    val imageUrl: String?,
                    val shippingPrice: Int,
                    val hidden: Boolean,
                    val deletedAt: Any
                ) {
                    data class Merchant(
                        val id: Int,
                        val name: String
                    )
                }
            }
        }
    }
}