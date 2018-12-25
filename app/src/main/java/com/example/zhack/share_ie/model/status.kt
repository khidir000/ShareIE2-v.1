package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class status (
        @SerializedName("status")
        var status:Int?=null,
        @SerializedName("detail")
        var detail:List<DataBerita>,
        @SerializedName("message")
        var message:String?=null
)