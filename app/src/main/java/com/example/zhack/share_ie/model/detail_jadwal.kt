package com.example.zhack.share_ie.model

import com.google.gson.annotations.SerializedName

data class detail_jadwal(
        @SerializedName("id_jadwal")
        var id_jadwal:String?=null,
        @SerializedName("jam_mulai")
        var jam_mulai:String?=null,
        @SerializedName("jam_berakhir")
        var jam_berakhir:String?=null,
        @SerializedName("nama_mk")
        var nama_mk:String?=null,
        @SerializedName("dosen_mk")
        var dosen_mk:String?=null,
        @SerializedName("ruangan_mk")
        var ruangan_mk:String?=null,
        @SerializedName("day")
        var day:String?=null
)