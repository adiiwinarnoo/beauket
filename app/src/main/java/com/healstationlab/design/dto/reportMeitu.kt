package com.healstationlab.design.dto

data class reportMeitu(
    val responseCode: String,
    val message: String,
    val `data`: List<Data>
) {
    data class Data(
        val report_id: Int,
        val uid: Int,
        val merchant_id: Int,
        val store_id: Long,
        val store_name: String,
        val account_id: Long,
        val account_username: String,
        val device_id: String,
        val created_at: Int,
        val preview: Preview,
        val pore: Pore,
        val blackhead: Blackhead,
        val wrinkle: Wrinkle,
        val speckle: Speckle,
        val acne: Acne,
        val sensitive: Sensitive,
        val black_rim_of_eye: BlackRimOfEye,
        val pic_list: PicList?,
        val paint_mask: PaintMask
    ) {
        data class Preview(
            val skin_age: Int,
            val age: Int,
            val skin_type: Int,
            val t_score: Double,
            val cheek_score: Double,
            val chin_score: Double,
            val color: String,
            val color_code: String,
            val tone: String,
            val slsd: Slsd,
            val pfpz: Pfpz,
            val hssdx: Hssdx
        ) {
            data class Slsd(
                val score: Int,
                val level: Int,
                val sub_item: SubItem
            ) {
                data class SubItem(
                    val kyhl: Kyhl,
                    val kthl: Kthl,
                    val kglhl: Kglhl
                ) {
                    data class Kyhl(
                        val score: Int,
                        val level: Int
                    )

                    data class Kthl(
                        val score: Int,
                        val level: Int
                    )

                    data class Kglhl(
                        val score: Int,
                        val level: Int
                    )
                }
            }

            data class Pfpz(
                val score: Int,
                val level: Int,
                val sub_item: SubItem
            ) {
                data class SubItem(
                    val ssl: Ssl,
                    val kjl: Kjl,
                    val kyl: Kyl
                ) {
                    data class Ssl(
                        val score: Int,
                        val level: Int
                    )

                    data class Kjl(
                        val score: Int,
                        val level: Int
                    )

                    data class Kyl(
                        val score: Int,
                        val level: Int
                    )
                }
            }
            
            data class Hssdx(
                val score: Int,
                val level: Int,
                val sub_item: SubItem
            ) {
                data class SubItem(
                    val ybl: Ybl,
                    val khl: Khl
                ) {
                    data class Ybl(
                        val score: Int,
                        val level: Int
                    )

                    data class Khl(
                        val score: Int,
                        val level: Int
                    )
                }
            }
        }

        data class Pore(
            val density: Double,
            val percentage: Double,
            val jzs_density: Double,
            val jzs_percentage: Double,
            val type: Int,
            val degree: Int,
            val cleanliness_type: Int,
            val score: Int,
            val jzs_score: Int
        )

        data class Blackhead(
            val degree: Int,
            val density: Double,
            val percentage: Double,
            val score: Int
        )

        data class Wrinkle(
            val density: Double,
            val degree: Int,
            val trouble_list: List<Trouble>,
            val percentage: Double,
            val score: Int
        ) {
            data class Trouble(
                val type: Int,
                val degree: Int,
                val detect: Int
            )
        }

        data class Speckle(
            val t: List<T>,
            val cheek: List<Cheek>,
            val sum: Sum,
            val score: Int,
            val degree: Int
        ) {
            data class T(
                val type: Int,
                val quantity: Int,
                val percentage: Double
            )

            data class Cheek(
                val type: Int,
                val quantity: Int,
                val percentage: Double
            )

            data class Sum(
                val quantity: Int,
                val percentage: Double
            )
        }

        data class Acne(
            val t: List<T>,
            val cheek: List<Cheek>,
            val chin: List<Chin>,
            val sum: Sum,
            val zz_density: Double,
            val level: Int,
            val degree: Int,
            val possibility: Double,
            val score: Int,
            val zz_score: Int
        ) {
            data class T(
                val quantity: Int
            )

            data class Cheek(
                val quantity: Int
            )

            data class Chin(
                val quantity: Int
            )

            data class Sum(
                val quantity: Int,
                val percentage: Double
            )
        }

        data class Sensitive(
            val type: Int,
            val degree: Int,
            val percentage: Double,
            val area: Int,
            val score: Int
        )

        data class BlackRimOfEye(
            val degree: Int,
            val score: Int
        )

        data class PicList(
            val `0`: X0,
            val `1`: X1,
            val `2`: X2,
            val `4`: X4,
            val `5`: X5,
            val `6`: X6
        ) {
            data class X0(
                val `0`: String,
                val `1`: String,
                val `2`: String
            )

            data class X1(
                val `0`: String,
                val `1`: String,
                val `2`: String
            )

            data class X2(
                val `0`: String,
                val `1`: String,
                val `2`: String
            )

            data class X4(
                val `0`: String,
                val `1`: String,
                val `2`: String
            )

            data class X5(
                val `0`: String,
                val `1`: String,
                val `2`: String
            )

            data class X6(
                val `0`: String,
                val `1`: String,
                val `2`: String
            )
        }

        data class PaintMask(
            val `849`: String,
            val `258`: String,
            val `1314`: String,
            val `1792`: String,
            val `512`: String,
            val `1025`: String,
            val `770`: String,
            val `513`: String,
            val `1280`: String,
            val `290`: String,
            val `1793`: String,
            val `1632`: String,
            val `514`: String,
            val `1026`: String,
            val `850`: String,
            val `832`: String,
            val `1794`: String,
            val `1633`: String,
            val `1312`: String,
            val `1281`: String,
            val `768`: String,
            val `833`: String,
            val `288`: String,
            val `769`: String,
            val `256`: String,
            val `1282`: String,
            val `834`: String,
            val `1634`: String,
            val `1313`: String,
            val `289`: String,
            val `257`: String,
            val `848`: String,
            val `1024`: String
        )
    }
}