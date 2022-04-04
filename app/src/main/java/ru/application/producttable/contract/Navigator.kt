package ru.application.producttable.contract

import androidx.fragment.app.Fragment

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator = requireActivity() as Navigator

interface Navigator {

    fun updateUI()

    fun goBack()

    fun goToSettings()

    fun goToReceiptDetail(id: String)

    fun goToGetReceiptData(qr: String)

}
