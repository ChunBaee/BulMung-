package com.jcorp.onmyway

import com.jcorp.onmyway.data.JsonData
import com.jcorp.onmyway.data.PostData
import com.jcorp.onmyway.data.Result

interface MainActivityView {
    fun onGetDataSuccess(response : JsonData)

    fun onPostDataSuccess(response : Result)
}