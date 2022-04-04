package ru.application.producttable.presentation.fragments.fragmentallreceipt.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.application.producttable.domain.usecases.DeleteReceiptUseCase
import ru.application.producttable.domain.usecases.GetReceiptsUseCase
import ru.application.producttable.presentation.fragments.fragmentallreceipt.ListReceiptsVM

class ListReceiptsVMFactory(
    private val getReceiptsUseCase: GetReceiptsUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase,
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ListReceiptsVM(
            getReceiptsUseCase = getReceiptsUseCase,
            deleteReceiptUseCase = deleteReceiptUseCase
        ) as T
    }

}
