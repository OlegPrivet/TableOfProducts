package ru.application.producttable.domain.entities.remote

import com.google.gson.annotations.SerializedName

data class ReceiptBody(

    @SerializedName("fn")
    val fn: String,

    @SerializedName("fd") /** @param i from qr*/
    val fd: String,

    @SerializedName("fp")
    val fp: String,

    @SerializedName("t")
    val t: String,

    @SerializedName("n")
    val n: String,

    @SerializedName("s")
    val s: String,

    @SerializedName("qr")
    val qr: String = "1",

    @SerializedName("token")
    val token : String,
)
