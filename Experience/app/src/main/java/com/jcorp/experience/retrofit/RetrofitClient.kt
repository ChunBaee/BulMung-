package com.jcorp.experience.retrofit

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit

object RetrofitClient {
    private var instance : Retrofit? = null
    fun getInstance (baseUrl : String) : Retrofit {
        if(instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
                .build()
        }
        return instance!!
    }
}