package com.example.zhack.share_ie.API

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface RequestData{
    @GET("search_all_teams.php?l=Italian%20Serie%20A")
    abstract fun getItemDetail(): Call<DataCategory>

    companion object {
        val BASE_URL = "https://thesportsdb.com/api/v1/json/1/"
        fun create(): RequestData {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            return retrofit.create(RequestData::class.java)
        }
    }
}