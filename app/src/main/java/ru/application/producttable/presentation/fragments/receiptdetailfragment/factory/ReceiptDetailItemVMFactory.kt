package ru.application.producttable.presentation.fragments.receiptdetailfragment.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.application.producttable.domain.usecases.DeleteReceiptUseCase
import ru.application.producttable.domain.usecases.GetLocalReceiptUseCase
import ru.application.producttable.presentation.fragments.receiptdetailfragment.ReceiptDetailItemVM

class ReceiptDetailItemVMFactory(
    private val getLocalReceiptUseCase: GetLocalReceiptUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ReceiptDetailItemVM(
            getLocalReceiptUseCase = getLocalReceiptUseCase,
            deleteReceiptUseCase = deleteReceiptUseCase
        ) as T
    }
}
