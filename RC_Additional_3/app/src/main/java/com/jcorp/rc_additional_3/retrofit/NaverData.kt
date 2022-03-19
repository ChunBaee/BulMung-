package com.jcorp.rc_additional_3.retrofit

data class NaverData(
    val message: String,
    val response: Response,
    val resultcode: String
)

data class Response(
    val email: String,
    val id: String,
    val name: String,
    val nickname: String,
    val profile_image: String
)