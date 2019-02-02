package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class status_jadwal(
       @SerializedName("status")
       var status:Int? = null,
       @SerializedName("detail")
       var detail:List<detail_jadwal>
)