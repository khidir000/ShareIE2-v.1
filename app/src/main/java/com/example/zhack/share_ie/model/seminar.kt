package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class seminar(
        @SerializedName("error")
        var error:Boolean?=null,
        @SerializedName("id")
        var id:String?=null,
        @SerializedName("username")
        var username:String?=null,
        @SerializedName("name")
        var name:String?=null,
        @SerializedName("foto")
        var foto:String?=null,
        @SerializedName("id_seminar")
        var id_seminar:String?=null,
        @SerializedName("judul")
        var judul:String?=null,
        @SerializedName("lokasi")
        var lokasi:String?=null,
        @SerializedName("tanggal")
        var tanggal:String?=null,
        @SerializedName("waktu")
        var waktu:String?=null,
        @SerializedName("jenis")
        var jenis:String?=null

)