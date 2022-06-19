package com.healstationlab.design.dto

data class address(
    val results : Result?
)

data class Common(
        val totalCount : String,
        val currentPage : Int,
        val countPerPage : Int,
        val errorCode : String,
        val errorMessage : String
)
data class Juso(
    val roadAddr : String,
    val roadAddrPart1 : String,
    val roadAddrPart2 : String,
    val jibunAddr : String,
    val engAddr : String,
    val zipNo : String,
    val admCd : String,
    val rnMgtSn : String,
    val bdMgtSn : String,
    val detBdNmList : String,
    val bdNm : String,
    val bdKdcd : String,
    val siNm : String,
    val sggNm : String,
    val emdNm : String,
    val liNm : String,
    val rn : String,
    val udrtYn : String,
    val buldMnnm : Number,
    val buldSlno : Number,
    val mtYn : String,
    val lnbrMnnm : Number,
    val lnbrSlno : Number,
    val emdNo : String,
    val hstryYn : String,
    val relJibun : String,
    val hemdNm : String
)

data class Result(
    val common : Common,
    val juso : List<Juso>
)