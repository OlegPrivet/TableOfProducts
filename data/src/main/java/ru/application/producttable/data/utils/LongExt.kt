package ru.application.producttable.data.utils

import android.annotation.SuppressLint
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Long.getDate(): String = SimpleDateFormat("dd.MM.yyyy").format(Date(this))

fun Long.toAmount() = DecimalFormat().format(BigDecimal(this).movePointLeft(2))
