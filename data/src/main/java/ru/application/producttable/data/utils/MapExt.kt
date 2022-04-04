package ru.application.producttable.data.utils

import com.google.gson.Gson

fun <K, T> Map<K, T>.getJson(): String {
    val gson = Gson()
    return gson.toJson(this)
}
