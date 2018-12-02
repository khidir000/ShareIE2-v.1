package com.example.zhack.share_ie.API

import com.google.gson.annotations.SerializedName

data class DataBerita (


        @SerializedName("id")
        var id:String? = null,

        @SerializedName("username")
        var username:String? = null,

        @SerializedName("name")
        var name:String? = null,

        @SerializedName("foto")
        var foto:String? = null,

        @SerializedName("user_id")
        var user_id:String? = null,

        @SerializedName("id_berita")
        var id_berita:String? = null,

        @SerializedName("isi_berita")
        var isi_berita:String? = null,

        @SerializedName("gambar")
        var gambar:String? = null,

        @SerializedName("create_at")
        var create_at:String? = null,

        @SerializedName("komentar")
        var komentar:List<DataKomentar>
)