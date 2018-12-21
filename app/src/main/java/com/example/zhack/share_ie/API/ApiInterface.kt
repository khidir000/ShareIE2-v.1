package com.example.zhack.share_ie.API

import com.example.zhack.share_ie.model.DataBerita
import com.example.zhack.share_ie.model.User
import retrofit2.Call
import retrofit2.http.*

public interface ApiInterface {
    @Headers(
            "Accept : application/json"
    )
    @POST("auth/login")
    abstract fun performUserLogin(@Header("Authorization")Auth: String?,
                                  @Query("username") UserName:String,
                                  @Query("password") Password:String)
            : Call<User>

    @GET("berita")
    abstract fun getBerita(@Header("User-Id")UserId:String?,
                           @Header("Authorization")Auth:String?)
            : Call<DataBerita>

}