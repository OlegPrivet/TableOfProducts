package ru.application.producttable.contract

import androidx.annotation.StringRes

interface HasCustomTitle {

    @StringRes
    fun getTitleRes(): Int?

    fun getTitleStr(): String
}
