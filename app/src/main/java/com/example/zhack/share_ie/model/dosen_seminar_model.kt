package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class dosen_seminar_model(
        @SerializedName("id_dsnsmr")
        var id_dsnsmr:String?=null,
        @SerializedName("jenis")
        var jenis:String?=null,
        @SerializedName("dosen")
        var dosen:String?=null,
        @SerializedName("id_smr")
        var id_smr:String?=null
)