package com.jcorp.onmyway.data

data class PostData(
    val result: Result,
    val code: Int,
    val isSuccess: Boolean,
    val message: String
)