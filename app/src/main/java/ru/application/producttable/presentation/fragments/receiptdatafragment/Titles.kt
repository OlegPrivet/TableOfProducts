package ru.application.producttable.presentation.fragments.receiptdatafragment

import androidx.annotation.StringRes
import ru.application.producttable.R

enum class Titles(@StringRes val title: Int) {
    ENTER_TARGET(R.string.receipt_target),
    LOADING(R.string.loading),
    RECEIPT_GET_DATA(R.string.receipt_get_data),
    RECEIPT_SAVE_DATA(R.string.receipt_save_data),
    ERROR(R.string.receipt_get_data_error);
}
