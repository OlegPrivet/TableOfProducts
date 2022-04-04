package ru.application.producttable.presentation.fragments.receiptdatafragment.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.application.producttable.domain.usecases.GetReceiptByQRUseCase
import ru.application.producttable.domain.usecases.SaveReceiptUseCase
import ru.application.producttable.presentation.fragments.receiptdatafragment.ReceiptFetchDataVM

class ReceiptFetchDataVMFactory(
    private val getReceiptByQRUseCase: GetReceiptByQRUseCase,
    private val saveReceiptUseCase: SaveReceiptUseCase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return ReceiptFetchDataVM(
            getReceiptByQRUseCase = getReceiptByQRUseCase,
            saveReceiptUseCase = saveReceiptUseCase
        ) as T
    }
}
