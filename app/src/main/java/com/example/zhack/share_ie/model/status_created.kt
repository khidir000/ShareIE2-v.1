package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class status_created(
        @SerializedName("user_id")
        var user_id:String?=null,
        @SerializedName("isi_berita")
        var isi_berita:String?=null
)