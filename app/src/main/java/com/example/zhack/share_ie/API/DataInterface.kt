package com.example.zhack.share_ie.API

import com.google.gson.annotations.SerializedName

data class DataInterface(
        @SerializedName("idTeam")
        var teamId:String? = null,

        @SerializedName("strTeam")
        var teamName: String? = null,

        @SerializedName("strTeamBadge")
        var teamBadge: String? = null,

        @SerializedName("strManager")
        var strDescriptionEN:String?=null

)