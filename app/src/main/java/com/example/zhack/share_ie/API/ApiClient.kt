package com.example.zhack.share_ie.API

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {

//    public val BASE_URL: String = "https://elennovation.com/khidir/index.php/"
//    public var retrofit: Retrofit? =null
//
//
//    public fun getApiClient(): Retrofit? {
//        if (retrofit == null){
//            retrofit =  Retrofit.Builder().baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build()
//        }
//        return retrofit
//    }

//    @GET("search_all_teams.php?l=Italian%20Serie%20A")
//    abstract fun getItemDetail(): Call<DataCategory>


    @Headers(

            "Accept:application/json",
            "Content-Type: application/json",
            "Client-Service:frontend-client",
            "Auth-Key:simplerestapi"
    )
    @GET("berita")
    abstract fun getDetail(@Header("User-Id")UserId:String?,
                           @Header("Authorization")Auth:String?)
            : Call<List<DataBerita>>


    @Headers(

            "Accept:application/json",
            "Content-Type: application/json",
            "Client-Service:frontend-client",
            "Auth-Key:simplerestapi"
    )
    @POST("auth/login")
    abstract fun getUser(@Query("username") UserName:String,
                         @Query("password") Password:String):Call<User>

    companion object {
        val BASE_URL = "https://elennovation.com/khidir/index.php/"
        fun create(): ApiClient {

            val gson = GsonBuilder().setLenient().create()
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
            return retrofit.create(ApiClient::class.java)
        }
    }
}