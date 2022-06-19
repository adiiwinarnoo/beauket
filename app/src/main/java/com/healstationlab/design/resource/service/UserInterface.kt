package com.healstationlab.design.resource.service

import com.healstationlab.design.dto.*
import com.healstationlab.design.resource.App
import com.healstationlab.design.resource.Constant
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UserInterface {

//    /** 메이투 유저 정보 **/
//    @POST(Constant.GET_USER) // const val GET_USER = "data/lists.json" // 메이투
//    fun getUserInfo(
//        @Query("app_id") app_id : String = "S1VY77iZY1XYJZ8snLuqflRVYztuIipk",
//        @Query("begin") begin : Int = 1615279071,
//        @Query("data_type") data_type : String = "user",
//        @Query("end") end : Int,
//        @Query("sig") sig : String,
//        @Query("sig_time") sig_time : Int,
//        @Query("source_type") source_type : String = "eve"
//    ) : Call<user>

    /** 메이투 유저 검사 결과 **/
//    @POST(Constant.GET_USER) // const val GET_USER = "data/lists.json" // 메이투
//    fun getUserReport(
//            @Query("app_id") app_id : String = "S1VY77iZY1XYJZ8snLuqflRVYztuIipk",
//            @Query("begin") begin : Int = 1615279071,
//            @Query("data_type") data_type : String = "report",
//            @Query("end") end : Int,
//            @Query("sig") sig : String,
//            @Query("sig_time") sig_time : Int,
//            @Query("source_type") source_type : String = "eve"
//    ) : Call<report>

    /** 도로명 주소 **/
    @GET(Constant.GET_ADDRESS)
    fun getAddress(
            @Query("confmKey") confmKey : String = "U01TX0FVVEgyMDIxMDQyODExMDIzMTExMTEwNDM=",
            @Query("currentPage") currentPage : Int = 1,
            @Query("countPerPage") countPerPage : Int = 10,
            @Query("keyword") keyword : String,
            @Query("resultType") resultType : String = "json"
    ) : Call<address>

    /** 회원가입 **/
    @POST(Constant.SIGN_UP)
    fun signUp(
        @Body body : HashMap<String ,String?>
    ) : Call<auth>

    /** 로그인 **/
    @POST(Constant.LOGIN)
    fun login(
        @Body body : HashMap<String ,String>
    ) : Call<auth>

    /** 배너 조회 **/
    @GET(Constant.BANNER)
    fun getBanner(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<banner>

    /** 이벤트 조회 **/
    @GET(Constant.EVENT)
    fun getEvent(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<event>

    /** 수다방 조회 **/
    @GET(Constant.BOARDS)
    fun getChat(
        @Query("pageNumber") pageNumber : Int = 0,
        @Query("pageSize") pageSize : Int = 10,
        @Query("keyword") keyword : String,
        @Query("category") category : String,
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<chat>

    /** 팝업 조회 **/
    @GET(Constant.POPUP)
    fun getPopup(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<popup>

    /** 카테고리 **/
    @GET(Constant.CATEGORY)
    fun getCategory(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<category>

    @GET(Constant.CATEGORY)
    fun getCategoryProduct(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("parentId") parentId : Any
    ) : Call<category>

    /** 제품추천 AI **/
    @GET(Constant.RECOMMEND_AI)
    fun getRecommendAi(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Query("pageNumber") pageNumber : Int = 0,
            @Query("pageSize") pageSize : Int = 10,
            @Query("skinType") skinType : String = App.prefs.getStringData(Constant.MANSAE_SKIN_TYPE).toString(),
            @Query("skinProblem") skinProblem : String = App.prefs.getStringData(Constant.SKIN_PROBLEM).toString(),
            @Query("ageFrom") ageFrom : Any = "",
            @Query("ageTo") ageTo : Any = "",
            @Query("keyword") keyword : String = "",
            @Query("recommendationProductCode") recommendationProductCode : String = App.prefs.getStringData(Constant.CODE).toString(),
            @Query("categoryIds") categoryIds : String = ""
    ) : Call<recommend>

    /** 특가 **/
    @GET(Constant.RECOMMEND_DEAL)
    fun getDeal(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("pageNumber") pageNumber : Int = 0,
        @Query("pageSize") pageSize : Int = 10,
        @Query("skinType") skinType : String = App.prefs.getStringData(Constant.MANSAE_SKIN_TYPE).toString(),
        @Query("skinProblem") skinProblem : String = App.prefs.getStringData(Constant.SKIN_PROBLEM).toString(),
        @Query("ageFrom") ageFrom : Any = "",
        @Query("ageTo") ageTo : Any = "",
        @Query("keyword") keyword : String = "",
        @Query("recommendationProductCode") recommendationProductCode : String = App.prefs.getStringData(Constant.CODE).toString(),
        @Query("categoryIds") categoryIds : String = ""
    ) : Call<deal>

    /** 랭킹 **/
    @GET(Constant.RECOMMEND_RANK)
    fun getRank(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("pageNumber") pageNumber : Int = 0,
        @Query("pageSize") pageSize : Int = 10
    ) : Call<rank>

    /** 제품 단건 조회 **/
    @GET(Constant.PRODUCT_DETAIL)
    fun getProductDetail(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int
    ) : Call<detailProduct>

    /** 수다방 단건 조회 **/
    @GET(Constant.CHAT_DETAIL)
    fun getChatDetail(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int
    ) : Call<detailChat>

    /** 수다방 댓글 조회 **/
    @GET(Constant.CHAT_COMMENT)
    fun getChatComment(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int
    ) : Call<comment>

    /** 수다방 댓글 달기 **/
    @POST(Constant.COMMENT)
    fun postComment(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int,
        @Body body : HashMap<String ,String>
    ) : Call<Unit>


    /** 제품 문의 조회 **/
    @GET(Constant.INQUERY)
    fun getInquery(
            @Header("Authorization") authorization: String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<inquery>

    /** 제품 문의응답 조회 **/
    @GET(Constant.INQUERY_RESPONSE)
    fun getInqueryResponse(
            @Header("Authorization") authorization: String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id: Int
    ) : Call<inqueryResponse>

    /** 제품 리뷰 조회 **/
    @GET(Constant.REVIEW)
    fun getReView(
            @Header("Authorization") authorization: String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id: Int,
            @Query("pageNumber") pageNumber : Int = 0,
            @Query("pageSize") pageSize : Int = 5
    ) : Call<review>

    /** 상품 옵션 조회 **/
    @GET(Constant.OPTION)
    fun getOption(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int
    ) : Call<option>

    /** 장바구니에 추가 Call 제네릭 타입 auth response data 바뀔 때 수정**/
    @POST(Constant.ADD_CART)
    fun postAddCard(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int,
        @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 카트 제품 리스트 조회 **/
    @GET(Constant.CART_LIST)
    fun getCartList(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<cart>

    /** 카트 항목 삭제 **/
    @DELETE(Constant.CART_ITEM_DELETE)
    fun deleteCartItem(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int
    ) : Call<auth>

    /** 카트 항목 카운트 증가 **/
    @POST(Constant.CART_ITEM_COUNT_PLUS)
    fun cartItemPlus(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int
    ) : Call<auth>

    /** 카투 항목 카운트 감소 **/
    @POST(Constant.CART_ITEM_COUNT_MINUS)
    fun cartItemMinus(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int
    ) : Call<auth>


    /** 공지사항 **/
    @GET(Constant.PUBLIC_NOTICE)
    fun getPublicNotice(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("pageNumber") pageNumber : Int = 0,
        @Query("pageSize") pageSize : Int = 5
    ) : Call<PublicNotice>

    /** FAQ **/
    @GET(Constant.FAQ)
    fun getFaq(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("pageNumber") pageNumber : Int = 0,
        @Query("pageSize") pageSize : Int = 5
    ) : Call<faq>

    /** 쿠폰 조회 **/
    @GET(Constant.COUPON)
    fun getCoupon(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<coupon>

    /** 쿠폰 등록 **/
    @POST(Constant.COUPON)
    fun registerCoupon(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 배송지 조회 **/
    @GET(Constant.MY_ADDRESS)
    fun getMyAddress(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<myAddress>

    /** 배송지 추가, 수정 **/
    @POST(Constant.MY_ADDRESS)
    fun addEditAddress(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Body body: HashMap<String, Any>
    ) : Call<auth>

    /** 배송지 삭제 **/
    @DELETE(Constant.DELETE_MY_ADDRESS)
    fun deleteAddress(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int
    ) : Call<auth>

    /** 회원탈퇴 **/
    @DELETE(Constant.SIGN_OUT)
    fun signOut(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<auth>

    /** 제품 문의 하기 **/
    @POST(Constant.POST_INQUERY)
    fun postInquery(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id: Int,
        @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 출석체크, 포인트 조회 **/
    @GET(Constant.CALENDAR)
    fun getCalendar(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<calendar>

    /** 유저 정보 조회 **/
    @GET(Constant.USER_INFO)
    fun getUserInfo(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<userInfo>

    /** 닉네임 중복 체크 **/
    @GET(Constant.NICKNAME_CHECK)
    fun nicknameCheck(
        @Query("nickname") nickname : String,
        @Query("userId") userId : Int = App.prefs.getIntData(Constant.UID)
    ) : Call<auth>

    /** 닉네임 중복체크 헤더X **/
    @GET(Constant.NICKNAME_CHECK)
    fun nicknameCheckNoHeader(
            @Query("nickname") nickname : String
    ) : Call<auth>

    /** 유저 정보 수정 **/
    @PUT(Constant.USER_EDIT)
    fun editUserInfo(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("nickname") nickname : String,
        @Query("gender") gender : String,
        @Query("hasChildren") hasChildren : Boolean,
        @Query("birthDate") birthDate : String,
        @Query("skinType") skinType : String,
        @Query("skinProblems") skinProblems : ArrayList<String>
    ) : Call<auth>

    /** 설문하기 **/
     @POST(Constant.POST_QUESTION)
     fun postQuestion(
        @Header("Authorization") authorization: String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Body body: ArrayList<HashMap<String, ArrayList<Int>>>
     ) : Call<auth>

    /** 설문 응답 조회 **/
    @GET(Constant.POST_QUESTION)
    fun myQuestion(
        @Header("Authorization") authorization: String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<getQuestion>

    /** 숫자 카운트 **/
    @GET(Constant.MY_PAGE_COUNT)
    fun myPageCount(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<pageCount>

    /** 알림 설정 **/
    @PUT(Constant.ALARM)
    fun putAlarm(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("alarmEventOn") alarmEventOn : Boolean,
        @Query("alarmNoticeOn") alarmNoticeOn : Boolean,
        @Query("alarmOrderStatusOn") alarmOrderStatusOn : Boolean,
        @Query("alarmProductInquiryOn") alarmProductInquiryOn : Boolean,
        @Query("alarmInfoOn") alarmInfoOn : Boolean
    ) : Call<auth>

   /** 만ㅅㅔ 메이투 **/
    @GET(Constant.GET_MANSAE_REPORT)
    fun getMansaeMeituReport(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<reportMeitu>

    /** 출석 체크 **/
    @POST(Constant.CALENDAR_CHECK)
    fun postCheck(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("date") date : String
    ) : Call<auth>

    /** 수다방 글쓰기**/
       @Multipart
       @POST(Constant.BOARDS)
       fun postBoard(
           @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
           @Part contents : MultipartBody.Part,
           @Part category : MultipartBody.Part,
           @Part files : ArrayList<MultipartBody.Part>
       ) : Call<auth>

       /**Edit Comment**/
       @Multipart
       @POST(Constant.BOARDS)
       fun editBoard(
           @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
           @Part contents : MultipartBody.Part,
           @Part category : MultipartBody.Part,
           @Part files : ArrayList<MultipartBody.Part>,
           @Part id : MultipartBody.Part,
           @Part fileStatus : MultipartBody.Part
       ) : Call<auth>


       /**Delete Board**/
       @DELETE(Constant.DELETE_BOARD)
       fun deleteBoard(
           @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
           @Path("id") id : Int
       ) : Call<auth>


    /** 프로필 이미지 수정 **/
    @Multipart
    @POST(Constant.EDIT_PROFILE_IMAGE)
    fun postProfileImage(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Part file : MultipartBody.Part
    ) : Call<auth>

    /** 내 리뷰 조회 **/
    @GET(Constant.MY_REVIEW)
    fun getMyReview(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Query("pageNumber") pageNumber : Int = 0,
            @Query("pageSize") pageSize : Int = 20
    ) : Call<review>

    /** 구매 **/
    @POST(Constant.POST_PURCHASE)
    fun postPurchase(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Body body : HashMap<String, Any>
    ) : Call<postOrderDTO>

    /** 단어 가져오기 **/
    @GET(Constant.GET_WORD)
    fun getReviewWord(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<wordCount>

    /** 리뷰 쓰기 **/
    @Multipart
    @POST(Constant.WRITE_REVIEW)
    fun writeReview(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int,
            @Part rating : MultipartBody.Part,
            @Part content : MultipartBody.Part,
            @Part files : ArrayList<MultipartBody.Part>
    ) : Call<auth>

    /** 찜하기 **/
    @POST(Constant.FAVORITES)
    fun favorites(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int
    ) : Call<auth>

    /** 찜한 항목 **/
    @GET(Constant.FAVORITES_LIST)
    fun getFavorites(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Query("pageNumber") pageNumber : Int = 0,
            @Query("pageSize") pageSize : Int = 100
    ) : Call<favoriteDTO>

    /** 일반 문의 리스트 **/
    @GET(Constant.DEFAULT_INQUERY)
    fun defaultInquery(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<defaultInqueryDTO>

    /** 일반 문의 하기 **/
    @POST(Constant.DEFAULT_INQUERY)
    fun postInquery(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 주문내역 조회 **/
    @GET(Constant.MY_ORDER)
    fun getMyOrder(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<myOrderDTO>

    /** 주문내역 상태 변경 **/
    @PUT(Constant.CHANGE_STATUS)
    fun putStatus(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Path("id") id : Int,
            @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 주문 숨김처리 **/
    @PUT(Constant.ORDER_HIDDEN)
    fun putHidden(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int,
        @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 비밀번호 변경 **/
    @POST(Constant.NEW_PASSWORD)
    fun newPassword(
        @Body body: HashMap<String, String>
    ) : Call<auth>

    /** 카카오 **/
    @GET("v2/user/me")
    fun getInfo(
        @Header("Content-Type") contentType : String = "application/x-www-form-urlencoded; charset=UTF-8",
        @Header("Authorization") Authorization : String
    ) : Call<kakao>

    /** 네이버 **/
    @GET("v1/nid/me")
    fun getNaverInfo(
        @Header("Authorization") Authorization : String
    ) : Call<naverDTO>

    /** 사용자 피부 평균 값 **/
    @GET(Constant.SKIN_DATA)
    fun getMyScore(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Query("sensitiveScore") sensitiveScore : Int,
        @Query("speckleScore") speckleScore : Int,
        @Query("wrinkleScore") wrinkleScore : Int,
        @Query("acneScore") acneScore : Int,
        @Query("blackheadScore") blackheadScore : Int,
        @Query("blackRimOfEyeScore") blackRimOfEyeScore : Int,
        @Query("poreScore") poreScore : Int
    ) : Call<scoreResultDTO>

    /** 푸쉬 **/
    @POST(Constant.PUSH)
    fun pushToken(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Body body : HashMap<String, String>
    ) : Call<auth>

    /** 주문 취소 **/
    @GET(Constant.PAYMENT_CANCEL)
    fun paymentCancel(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
            @Query("paymethod") paymethod : String,
            @Query("tid") tid : String,
            @Query("price") price : Int,
            @Query("confirmPrice") confirmPrice : Int
    ) : Call<paymentCancelDTO>

    @GET(Constant.GET_POINT)
    fun getPoints(
            @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString()
    ) : Call<pointsDate>

    @POST(Constant.PAYMENT_CANCEL_ALL)
    fun postCancel(
        @Header("Authorization") authorization : String = App.prefs.getStringData(Constant.AUTH).toString(),
        @Path("id") id : Int
    ) : Call<paymentCancelDTO>

    @GET(Constant.FIND_ID)
    fun getId(
        @Query("contact") contact : String
    ) : Call<findIdDTO>




}