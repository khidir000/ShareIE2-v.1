package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class user_detail (
        @SerializedName("id")
        var id:String?=null,
        @SerializedName("username")
        var username:String?=null,
        @SerializedName("password")
        var password:String? = null,
        @SerializedName("name")
        var name:String?=null,
        @SerializedName("last_login")
        var last_login:String?=null,
        @SerializedName("created_at")
        var created_at:String?=null,
        @SerializedName("updated_at")
        var updated_at:String?=null,
        @SerializedName("foto")
        var foto:String?=null,
        @SerializedName("email")
        var email:String?=null,
        @SerializedName("tgl_lahir")
        var tgl_lahir:String?=null,
        @SerializedName("no_hp")
        var no_hp:String?=null,
        @SerializedName("alamat")
        var alamat:String?=null,
        @SerializedName("level")
        var level:String?=null,
        @SerializedName("Konsenstrasi")
        var konsentrasi:String?=null,
        @SerializedName("thn_masuk")
        var thn_masuk:String?=null
)