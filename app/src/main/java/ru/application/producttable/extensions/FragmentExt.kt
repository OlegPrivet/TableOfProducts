package ru.application.producttable.utils

import androidx.fragment.app.Fragment
import ru.application.producttable.ReceiptApp

fun Fragment.application() : ReceiptApp {
    return ((requireActivity().application) as ReceiptApp)
}
