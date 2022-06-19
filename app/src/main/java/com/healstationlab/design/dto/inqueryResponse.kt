package com.healstationlab.design.dto

data class inqueryResponse(
    val responseCode: String,
    val message: Any,
    val `data`: Data
) {
    data class Data(
        val id: Int,
        val inquiry: Inquiry,
        val content: String,
        val createdDate : String
    ) {
        data class Inquiry(
            val id: Int,
            val product: Product,
            val user: User,
            val content: String,
            val answered: Boolean
        ) {
            data class Product(
                val id: Int,
                val merchant: Merchant,
                val category: Category,
                val name: String,
                val advertisement: Boolean,
                val brand: String,
                val dosageForm: String,
                val cosmeticIngredients: Any,
                val hashTags: List<Any>,
                val features: List<Any>,
                val recommendedIngredients: List<Any>,
                val improvements: List<Any>,
                val skinType: Any,
                val description: String,
                val shippingPrice: Int,
                val recommendAgeStart: Any,
                val recommendAgeEnd: Any,
                val imageUrl: String,
                val details: String,
                val hidden: Boolean,
                val rating: Double,
                val options: List<Option>,
                val codes: List<Any>,
                val ratingCountMap: Any,
                val ingredientsStr: Any,
                val hashTagsStr: Any
            ) {
                data class Merchant(
                    val id: Int,
                    val name: String,
                    val businessNumber: String,
                    val contact: Any,
                    val zoneCode: String,
                    val address: String,
                    val addressDetail: String,
                    val bizCertFileUrl: String,
                    val smbCertFileUrl: String,
                    val bankbookFileUrl: String,
                    val plInsCertFileUrl: String,
                    val bankName: String,
                    val bankAccount: String,
                    val representativeName: Any,
                    val representativeCellphone: Any,
                    val representativeEmail: Any,
                    val saleName: Any,
                    val saleCellphone: Any,
                    val saleEmail: Any,
                    val balanceName: Any,
                    val balanceCellphone: Any,
                    val balanceEmail: Any,
                    val productName: Any,
                    val productCategory: Any,
                    val productPrice: Any,
                    val productTaxType: Any,
                    val productProductionType: Any,
                    val productBrandType: Any,
                    val productInfoFileUrl: Any,
                    val marketUrl: Any,
                    val license: Any,
                    val makingDocFileUrl: Any,
                    val licenseDocFileUrl: Any,
                    val foodQualityDocFileUrl: Any,
                    val bizReportDocFileUrl: Any,
                    val cosmeticCertDocFileUrl: Any,
                    val inspectDocFileUrl: Any,
                    val loginId: Any,
                    val commissionRate: Double,
                    val registered: Boolean,
                    val openDateFormatted: Any
                )

                data class Category(
                    val id: Int,
                    val name: String,
                    val iconImageUrl: String
                )

                data class Option(
                    val id: Int,
                    val name: String,
                    val price: Int,
                    val originalPrice: Int,
                    val stocks: Int
                )
            }

            data class User(
                val id: Int,
                val loginId: String,
                val thirdPartyId: Any,
                val name: Any,
                val nickname: Any,
                val email: Any,
                val contact: Any,
                val level: Int,
                val points: Int,
                val gender: Any,
                val username: String,
                val password: String,
                val analysisData: Boolean,
                val married: Boolean,
                val hasChildren: Boolean,
                val cosmeticProcedure: Boolean,
                val sensitiveConstitution: Boolean,
                val agreePrivacyPolicy: Boolean,
                val agreeTermsOfUse: Boolean,
                val agreeSms: Boolean,
                val agreeEmail: Boolean,
                val authType: String,
                val lastAccessAt: Any,
                val deletedAt: Any,
                val newPassword: Any,
                val enabled: Boolean,
                val authorities: Any,
                val accountNonExpired: Boolean,
                val accountNonLocked: Boolean,
                val credentialsNonExpired: Boolean
            )
        }
    }
}