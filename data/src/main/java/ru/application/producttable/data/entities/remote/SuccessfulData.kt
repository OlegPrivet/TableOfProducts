package ru.application.producttable.data.entities.remote

import com.google.gson.annotations.SerializedName

data class SuccessfulData(

    @SerializedName("json")
    val json: JsonResponse,

    @SerializedName("html")
    val html: String,
)
