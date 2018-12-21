package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class DataKomentar(
        @SerializedName("name")
        var name:String? = null,

        @SerializedName("username")
        var username:String? = null,

        @SerializedName("foto")
        var foto:String? = null,

        @SerializedName("isi_komentar")
        var isi_komentar:String? = null,

        @SerializedName("berita_id")
        var berita_id:String? = null
)