package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class status_seminar(

        @SerializedName("status")
        var status:Int?=null,
        @SerializedName("detail")
        var detail:List<seminar>,
        @SerializedName("message")
        var message:String?=null
)