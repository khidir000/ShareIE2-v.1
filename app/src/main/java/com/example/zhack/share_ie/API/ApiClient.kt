package com.example.zhack.share_ie.API

import com.example.zhack.share_ie.model.DataBerita
import com.example.zhack.share_ie.model.Komentar_isi
import com.example.zhack.share_ie.model.User
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiClient {

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

    @GET("berita")
    abstract fun getKoment(@Header("User-Id")UserId:String?,
                           @Header("Authorization")Auth:String?)
            : Call<Komentar_isi>

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