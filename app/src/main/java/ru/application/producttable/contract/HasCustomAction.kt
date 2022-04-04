package ru.application.producttable.contract

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface HasCustomAction {
    fun getCustomActions(): List<CustomAction>
}

class CustomAction(
    @DrawableRes val iconRes: Int,
    @StringRes val titleRes: Int,
    val onCustomAction: Runnable,
    val showAsAction: Int? = null
)
