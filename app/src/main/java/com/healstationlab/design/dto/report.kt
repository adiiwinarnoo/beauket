package com.healstationlab.design.dto

import com.google.gson.annotations.SerializedName

data class report(
        val meta : Meta,
        val response : Response_report
)

data class Meta(
        val code : Int,
        val msg : String,
        val error : String,
        val request_url : String
)

data class Response_report(
        val next : String,
        val lists : List<List_report?>
)

data class List_report(
        val report_id : Long,
        val uid : Long,
        val merchant_id : Long,
        val store_id : Long,
        val store_name : String,
        val account_id : Long,
        val account_username : String,
        val device_id : String,
        val created_at : String,
        val preview : Preview,
        val pore : Pore,
        val blackhead : BlackHead,
        val wrinkle : Wrinkle,
        val speckle : Speckle,
        val acne : Acne,
        val sensitive : Sensitive,
        val black_rim_of_eye : Black_rim_of_eye,
        val pic_list : Any,
        val paint_mask : Any
)

data class Preview(
        val skin_age : Int,
        val age : Int,
        val skin_type : Int,
        val t_score : Double,
        val cheek_score : Double,
        val chin_score : Double,
        val color : String,
        val color_code : String,
        val tone : String,
        val slsd : Slsd,
        val pfpz : Pfpz,
        val hssdx : Hssdx
)

data class Slsd(
        val score : Int,
        val level : Int,
        val sub_item : Sub_item_slsd
)

data class Sub_item_slsd(
        val kyhl : sub_item_info,
        val kthl : sub_item_info,
        val kglhl : sub_item_info
)

data class Pfpz(
        val score : Int,
        val level : Int,
        val sub_item : Sub_item_pfpz
)

data class Sub_item_pfpz(
        val ssl : sub_item_info,
        val kjl : sub_item_info,
        val kyl : sub_item_info
)

data class Hssdx(
        val score : Int,
        val level : Int,
        val sub_item : Sub_item_hssdx
)

data class Sub_item_hssdx(
        val ybl : sub_item_info,
        val khl : sub_item_info
)

data class sub_item_info(
        val score : Int,
        val level : Int
)

data class Pore(
        val density : Double,
        val percentage : Double,
        val jzs_density : Double,
        val jzs_percentage : Double,
        val type : Int,
        val degree : Int,
        val cleanliness_type : Int,
        val score : Int,
        val jzs_score : Int
)

data class BlackHead(
        val degree : Int,
        val density : Double,
        val percentage : Double,
        val score : Int
)

data class Wrinkle(
        val density : Double,
        val degree : Int,
        val trouble_list : List<trouble_list_item>,
        val percentage : Double,
        val score : Int
)

data class trouble_list_item(
        val type : Int,
        val degree : Int,
        val detect : Int
)

data class Speckle(
        val t : List<t_item>,
        val cheek : List<t_item>,
        val sum : Sum,
        val score : Int,
        val degree : Int
)

data class t_item(
        val type : Int,
        val quantity : Int,
        val percentage: Double
)

data class Acne(
    val t : List<acne_item>,
    val cheek : List<acne_item>,
    val chin : List<acne_item>,
    val sum : Sum,
    val zz_density : Double,
    val level : Int,
    val degree : Int,
    val possibility : Double,
    val score : Int,
    val zz_score : Int
)

data class acne_item(
        val quantity : Int
)

data class Sum(
        val quantity : Int,
        val percentage : Double
)

data class Sensitive(
        val type : Int,
        val degree : Int,
        val percentage : Double,
        val area : Long,
        val score : Int
)

data class Black_rim_of_eye(
        val degree : Int,
        val score : Int
)

data class Pic_list(
        @SerializedName("0")
        val `0` : pic_list_item,

        @SerializedName("1")
        val `1` : pic_list_item,

        @SerializedName("2")
        val `2` : pic_list_item,

        @SerializedName("4")
        val `4` : pic_list_item,

        @SerializedName("5")
        val `5` : pic_list_item,

        @SerializedName("6")
        val `6` : pic_list_item
)

data class pic_list_item(
        @SerializedName("0")
        val `0` : String,

        @SerializedName("1")
        val `1` : String,

        @SerializedName("2")
        val `2` : String
)

data class Paint_mask(
        @SerializedName("849")
        val `849` : String,

        @SerializedName("258")
        val `258` : String,

        @SerializedName("1314")
        val `1314` : String,

        @SerializedName("1792")
        val `1792` : String,

        @SerializedName("512")
        val `512` : String,

        @SerializedName("1025")
        val `1025` : String,

        @SerializedName("770")
        val `770` : String,

        @SerializedName("513")
        val `513` : String,

        @SerializedName("1280")
        val `1280` : String,

        @SerializedName("290")
        val `290` : String,

        @SerializedName("1793")
        val `1793` : String,

        @SerializedName("1632")
        val `1632` : String,

        @SerializedName("514")
        val `514` : String,

        @SerializedName("1026")
        val `1026` : String,

        @SerializedName("850")
        val `850` : String,

        @SerializedName("832")
        val `832` : String,

        @SerializedName("1794")
        val `1794` : String,

        @SerializedName("1633")
        val `1633` : String,

        @SerializedName("1312")
        val `1312` : String,

        @SerializedName("1281")
        val `1281` : String,

        @SerializedName("768")
        val `768` : String,

        @SerializedName("833")
        val `833` : String,

        @SerializedName("288")
        val `288` : String,

        @SerializedName("769")
        val `769` : String,

        @SerializedName("256")
        val `256` : String,

        @SerializedName("1282")
        val `1282` : String,

        @SerializedName("834")
        val `834` : String,

        @SerializedName("1634")
        val `1634` : String,

        @SerializedName("1313")
        val `1313` : String,

        @SerializedName("289")
        val `289` : String,

        @SerializedName("257")
        val `257` : String,

        @SerializedName("848")
        val `848` : String,

        @SerializedName("1024")
        val `1024` : String
)


