package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class status_user_detail(
        @SerializedName("status")
        var status:Int?=null,
        @SerializedName("detail")
        var detail:List<user_detail>,
        @SerializedName("message")
        var message:String?=null
)