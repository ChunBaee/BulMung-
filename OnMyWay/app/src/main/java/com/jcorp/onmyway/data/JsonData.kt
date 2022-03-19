package com.jcorp.onmyway.data

data class JsonData(
    val isSuccess : Boolean,
    val code : Int,
    val message : String,
    val result: List<Result>
)

data class Result(
    val name: String,
    val no: Int,
    val registeredTimestamp: String
)