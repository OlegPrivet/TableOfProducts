package ru.application.producttable.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import ru.application.producttable.data.constants.Constants.APP_SHARED
import ru.application.producttable.presentation.MainActivity

fun AppCompatActivity.shared() : SharedPreferences =
    this.getSharedPreferences(APP_SHARED, Context.MODE_PRIVATE)

fun Fragment.shared() : SharedPreferences =
    (activity as MainActivity).getSharedPreferences(APP_SHARED, Context.MODE_PRIVATE)
