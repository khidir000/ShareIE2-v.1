package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName


data class komen_status(
        @SerializedName("berita_id")
        var berita_id:Int,
        @SerializedName("isi_komentar")
        var isi_komentar:String?=null,
        @SerializedName("user_koment")
        var user_koment:Int
)