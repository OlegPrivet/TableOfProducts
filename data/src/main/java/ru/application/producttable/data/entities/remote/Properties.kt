package ru.application.producttable.data.entities.remote

import com.google.gson.annotations.SerializedName

data class Properties(

    @SerializedName("propertyName")
    val propertyName: String,

    @SerializedName("propertyValue")
    val propertyValue: String,
)
