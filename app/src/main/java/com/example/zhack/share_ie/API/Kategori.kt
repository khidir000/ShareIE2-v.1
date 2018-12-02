package com.example.zhack.share_ie.API

import com.google.gson.annotations.SerializedName
import retrofit2.Call


data class Kategori(
        val komen: Call<List<DataBerita>>
)