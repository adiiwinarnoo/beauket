package com.healstationlab.design.dto

data class review(
    val responseCode: String,
    val message: Any,
    val `data`: List<Data>,
    val totalCount: Int,
    val totalPages: Int,
    val currentPage: Int
) {
    data class Data(
        val id: Int,
        val product: Product,
        val userData: User,
        val content: String?,
        val imageUrl: String?,
        val imageUrls: List<String>,
        val rating: Double,
        val createdDate : String
    ) {
        data class Product(
            val id: Int,
            val merchant: Merchant,
            val category: Category,
            val name: String,
            val advertisement: Boolean,
            val advertisementTypes: String,
            val skinProblems: List<String>,
            val brand: String,
            val dosageForm: String,
            val cosmeticIngredients: Any,
            val code: Any,
            val hashTags: List<HashTag>,
            val dose: Any,
            val features: List<Any>,
            val recommendedIngredients: List<RecommendedIngredient>,
            val improvements: List<Improvement>,
            val effects: Any,
            val skinType: List<String>,
            val description: String,
            val shippingPrice: Int,
            val recommendAgeStart: Any,
            val recommendAgeEnd: Any,
            val imageUrl: String,
            val details: String,
            val hidden: Boolean,
            val reviewCount: Double,
            val rating: Double,
            val price: Double,
            val options: List<Option>,
            val codes: List<String>,
            val reviewWords : ArrayList<String>,
            val reviewWordCount : Int?,
            val skinTypeStr: String,
            val skinProblemStr: String,
            val codesStr: String,
            val ratingCountMap: Any,
            val ingredientsStr: Any,
            val hashTagsStr: Any,
            val category1st: Any,
            val category2nd: Any,
            val category3rd: Any,
            val improvementIdList: Any,
            val ingredientIdList: Any,
            val hashTagIdList: Any
        ) {
            data class Merchant(
                val id: Int,
                val name: String,
                val businessNumber: String,
                val contact: String,
                val zoneCode: String,
                val address: String,
                val addressDetail: String,
                val email: Any,
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
                val iconImageUrl: Any,
                val level: Int,
                val parent: Parent
            ) {
                data class Parent(
                    val id: Int,
                    val name: String,
                    val iconImageUrl: Any,
                    val level: Int,
                    val parent: Parent
                ) {
                    data class Parent(
                        val id: Int,
                        val name: String,
                        val iconImageUrl: String,
                        val level: Int,
                        val parent: Any
                    )
                }
            }

            data class HashTag(
                val id: Int,
                val name: String,
                val slsd: Int,
                val pfpz: Int,
                val hssdx: Int,
                val poreType: Int,
                val poreDegree: Int,
                val poreCleanlinessType: Int,
                val wrinkle: Int,
                val blackhead: Int,
                val createdAt: List<Int>,
                val updatedAt: List<Int>
            )

            data class RecommendedIngredient(
                val id: Int,
                val name: String,
                val hashTags: List<Any>,
                val hashTagIdList: Any
            )

            data class Improvement(
                val id: Int,
                val name: String,
                val createdAt: List<Int>,
                val updatedAt: List<Int>
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
            val name: String,
            val nickname: String?,
            val email: Any,
            val contact: String,
            val imageUrl: String?,
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
            val skinProblems: List<String>,
            val authType: String,
            val recommendProductCode: String,
            val newPassword: Any,
            val enabled: Boolean,
            val authorities: Any,
            val accountNonExpired: Boolean,
            val accountNonLocked: Boolean,
            val credentialsNonExpired: Boolean,
            val birthDate: String
        )
    }
}