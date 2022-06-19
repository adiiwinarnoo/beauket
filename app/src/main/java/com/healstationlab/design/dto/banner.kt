package com.healstationlab.design.dto

data class banner(
        val responseCode: String,
        val message: Any,
        val `data`: List<Data>
) {
    data class Data(
            val id: Int,
            val imageUrl: String,
            val linkUrl: String?,
            val product: Product?,
            val positionType: String,
            val linkType: String
    ) {
        data class Product(
                val id: Int,
                val merchant: Any,
                val category: Category,
                val name: String,
                val advertisement: Boolean,
                val advertisementTypes: String?,
                val brand: String,
                val dosageForm: Any,
                val cosmeticIngredients: Any,
                val code: String,
                val hashTags: List<Any>,
                val dose: String,
                val features: List<Any>,
                val recommendedIngredients: List<RecommendedIngredient>,
                val improvements: List<Any>,
                val effects: String,
                val skinType: List<Any>,
                val description: Any,
                val shippingPrice: Int,
                val recommendAgeStart: Any,
                val recommendAgeEnd: Any,
                val imageUrl: Any,
                val details: Any,
                val hidden: Boolean,
                val rating: Double,
                val options: List<Option>,
                val codes: List<String>,
                val ratingCountMap: Any,
                val ingredientsStr: Any,
                val hashTagsStr: Any
        ) {
            data class Category(
                    val id: Int,
                    val name: String,
                    val iconImageUrl: Any,
                    val parent: Parent
            ) {
                data class Parent(
                        val id: Int,
                        val name: String,
                        val iconImageUrl: Any,
                        val parent: Parent
                ) {
                    data class Parent(
                            val id: Int,
                            val name: String,
                            val iconImageUrl: String,
                            val parent: Any
                    )
                }
            }

            data class RecommendedIngredient(
                    val id: Int,
                    val name: String,
                    val hashTags: List<Any>
            )

            data class Option(
                    val id: Int,
                    val name: String,
                    val price: Int,
                    val originalPrice: Int,
                    val stocks: Int
            )
        }
    }
}

