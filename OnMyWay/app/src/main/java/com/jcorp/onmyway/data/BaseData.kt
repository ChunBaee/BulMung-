package com.jcorp.onmyway.data

import com.google.gson.annotations.SerializedName

open class BaseData(
    @SerializedName("code") val code: Int = 0,
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("message") val message: String? = "",
)
