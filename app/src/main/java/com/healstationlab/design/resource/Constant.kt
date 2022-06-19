package com.healstationlab.design.resource

import com.healstationlab.design.model.Cart

class Constant {
    companion object {

        /** Base_url**/
        const val BASE_URL_MANSAE = "http://110.10.130.52/api/" // 만세

        // http://110.10.130.52/api
        const val ADDRESS_BASE_URL = "https://www.juso.go.kr/" // 도로명 주소
        const val KAKAO = "https://kapi.kakao.com/"
        const val NAVER = "https://openapi.naver.com/"

        /** auth **/

        /** SharedPreference **/
        const val DEFAULT_POSITION = "default_position"
        const val UID = "userID" // 사용자 아이디
        const val EMAIL = "email"
        const val CODE = "recommendProductCode"
        const val SKIN_PROBLEM = "skin_problem"
        const val MANSAE_SKIN_TYPE = "mansae_skin_type"
        const val PASS_WORD = "userPassword"
        const val autoLogin = "autoLogin"
        const val PHONE ="phone"
        const val DELIVERY_PHONE = "delivery_phone"
        const val ADDRESS ="address"
        const val ADDRESS_NAME ="addressName"
        const val ADDRESS_CODE ="addressCode"
        const val ADDRESS_DETAIL ="addressDetail"
        const val AGE = "userAge" // 사용자 나이 (피부나이X)
        const val NAME = "userName" // 사용자 이름
        const val AUTH_ID = "authId"
        const val NICK_NAME = "nickName" // 닉네임
        const val SKIN_TYPE = "skinType" // 사용자 스킨 타입
        const val AUTH = "auth"
        const val AUTH_TYPE = "auth_type"
        const val DEFALUT_ADDRESS = "defalut_address" // 기본 배송지 정보 있는지 판단 여부
        const val DEFALUT_ADDRESS_ID = "defalut_address_id"


        const val PAYMENT_CHOOSE = "payment_choose"
        const val PRODUCT_QUERY_ID = "product_query_id"
        const val MEITU_DATA_CHECK = "meitu_data_check"
        const val STORE_NAME = "store_name"
        const val GENDER = "gender"
        const val BIRTH = "birth"

        var CURRENT_SKIN_POSITION = 0 // 피부 데이터 포지션
        var CEHCK = false // 첫 호출 체크
        var ORDER = false // 주문 이동 첫 체크 호출
        var meituCheck = true // main에서 메이투 분기하는거 체크

        /**For Show One Time**/
        var STATUS_CHECK = true
//        var chatId = 0

        var today = ""
        var ca_today = ""
        var categoryId = ""
        var edit_check = true
        var kakaoToken = ""

        var POPUP_CHECK = false

        lateinit var cart : ArrayList<Cart>


        /** API Call **/
//        const val GET_USER = "data/lists.json" // 메이투
        const val GET_ADDRESS = "addrlink/addrLinkApi.do" // 도로명주소

        /** 만세 API **/
        const val LOGIN = "users/authentications" // 로그인
        const val SIGN_UP = "users" // 회원가입
        const val BANNER = "banners" // 배너 조회
        const val POPUP = "popups" // 팝업
        const val EVENT = "events" // 이벤트
        const val BOARDS = "boards" // 수다방
        const val CATEGORY = "product-categories" // 카테고리
        const val RECOMMEND_AI = "products/recommendations/by-ai" // 제품추천 AI
//        const val RECOMMEND_PICK = "products/recommendations/by-pick" // 제품 추천
        const val RECOMMEND_DEAL = "products/recommendations/by-deal" // 제품 특가
        const val RECOMMEND_RANK = "products/ranks" // 제품 랭킹
        const val PRODUCT_DETAIL = "products/{id}" // 제품 단건 조회
        const val CHAT_DETAIL = "boards/{id}" // 수다방 단건 조회
        const val CHAT_COMMENT = "boards/{id}/comments" // 수다방 댓글 조회
//        const val POST_BOARDS = "products/{id}/inquiries" // 문의하기
        const val COMMENT = "boards/{id}/comments" // 댓글달기
        const val OPTION = "products/{id}/options" // 제품 옵션 조회
        const val INQUERY = "users/me/product-inquiries" // 제품 문의 조회
        const val INQUERY_RESPONSE = "product-inquiries/{id}/answers" // 제품 문의응답 조회
        const val PUBLIC_NOTICE = "notices" // 공지사항
        const val FAQ = "faqs" // FAQ
        const val REVIEW = "products/{id}/reviews" // 제품 리뷰 조회
//        const val REVIEW_RESPONSE = "product-reviews/{id}/answers" // 제품 리뷰 응답 조회(디자인 파일 없음)
        const val WRITE_REVIEW = "product-orders/{id}/reviews" // 리뷰쓰기
        const val MY_REVIEW = "users/me/products/reviews" // 내가쓴 리뷰 조회
        const val ADD_CART = "product-options/{id}/shopping-carts" // 카트 제품 추가
        const val CART_LIST = "users/shopping-carts" // 카트 제품 리스트 조회
        const val CART_ITEM_DELETE = "shopping-carts/{id}" // 카트 항목 삭제
        const val CART_ITEM_COUNT_PLUS = "shopping-carts/{id}/increase" // 카트 제품 카운트 증가
        const val CART_ITEM_COUNT_MINUS = "shopping-carts/{id}/decrease" // 카트 제품 카운트 감소
        const val COUPON = "users/coupons" // 쿠폰 등록, 조회
        const val MY_ADDRESS = "users/shipment-addresses" // 배송주소 조회, 배송주소 추가/수정
        const val DELETE_MY_ADDRESS = "users/shipment-addresses/{id}" // 배송지 삭제
        /**Add Adi**/
        const val DELETE_BOARD = "/api/boards/{id}" // 배송지 삭제
        const val SIGN_OUT = "users/me" // 회원탈퇴
        const val POST_INQUERY = "products/{id}/inquiries" // 문의하기
        const val GET_MANSAE_REPORT = "users/report-data" // 메이투에서 가져온 만세 데이터
            const val USER_INFO = "users-info" // 유저 정보 조회
        const val USER_EDIT = "users/me" // 유저 정보 변경
        const val NICKNAME_CHECK = "users/nickname/check" // 유저 닉네임 체크
        const val POST_QUESTION = "questions/answers" // 설문하기, 설문 응답 조회
        const val MY_PAGE_COUNT = "users/me/count-meta" // 마이페이지 카테고리 카운트
        const val ALARM = "users/me/alarm" // 알람 설정 조회
        const val CALENDAR = "users/me/check-attendance-dates" // 출석체크 조회
        const val CALENDAR_CHECK = "users/check-attendances" // 출석체크
        const val EDIT_PROFILE_IMAGE = "users/me/profile-image" // 프로필 이미지 수정
        const val POST_PURCHASE = "purchases" // 주문하기
        const val GET_WORD = "review-words" // 리뷰단어
        const val FAVORITES_LIST = "users/me/products/favorite" // 찜하기 리스트 조회
        const val DEFAULT_INQUERY = "inquiries" // 일반 문의, 문의 조회
        const val FAVORITES = "products/{id}/favorites/toggle" // 찜하기 토글
        const val MY_ORDER = "product-order-metas" // 주문내역 조회
        const val CHANGE_STATUS = "product-orders/{id}" // 주문상태 변경
        const val ORDER_HIDDEN = "product-order-metas/{id}" // 주문 숨김처리
        const val NEW_PASSWORD = "users/password" // 비밀번호 변경
        const val PAYMENT_CANCEL = "refunds-partial" // 주문 취소
        const val PUSH = "users/push-tokens" // 푸쉬
        const val SKIN_DATA = "users/skin-data" // 유저 피부 데이터
        const val GET_POINT = "points-setting" // 포인트 설정 조회
        const val PAYMENT_CANCEL_ALL = "refunds/all/product-order-metas/{id}" // 주문 전체 취소
        const val FIND_ID = "users-loginId" // 아이디 찾기
    }
}