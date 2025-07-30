package sszj.s.adblockerapp.models

import com.google.gson.annotations.SerializedName

//data class WebItem(
//    val id: Int,
//    val category: String,
//    val title: String,
//    val url: String
//
//
//)
//
data class WebItem(
    @SerializedName("id")
    val id: Int,

    @SerializedName("category")
    val category: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("url")
    val url: String
)
