package ru.application.producttable.presentation.fragments.fragmentallreceipt

import androidx.lifecycle.ViewModel
import ru.application.producttable.domain.usecases.DeleteReceiptUseCase
import ru.application.producttable.domain.usecases.GetReceiptsUseCase

class ListReceiptsVM(
    private val getReceiptsUseCase: GetReceiptsUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase,
) : ViewModel() {

    suspend fun getAllReceipts() = getReceiptsUseCase.invoke()

}
