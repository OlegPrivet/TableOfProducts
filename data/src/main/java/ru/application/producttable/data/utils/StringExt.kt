package ru.application.producttable.data.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.dateToLong(): Long {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return df.parse(this).time
}

