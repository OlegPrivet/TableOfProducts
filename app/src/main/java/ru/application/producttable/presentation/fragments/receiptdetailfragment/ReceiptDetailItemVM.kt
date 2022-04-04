package ru.application.producttable.presentation.fragments.receiptdetailfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import ru.application.producttable.domain.entities.local.LocalReceipt
import ru.application.producttable.domain.usecases.DeleteReceiptUseCase
import ru.application.producttable.domain.usecases.GetLocalReceiptUseCase

class ReceiptDetailItemVM(
    private val getLocalReceiptUseCase: GetLocalReceiptUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase,
) : ViewModel() {

    private val _localReceipt = MutableStateFlow(LocalReceipt())
    val localReceipt: StateFlow<LocalReceipt> = _localReceipt.asStateFlow()
    var job: Job? = null

    suspend fun getReceipt(id: String) {
        job = viewModelScope.launch {
            getLocalReceiptUseCase.invoke(id)
                .cancellable()
                .collect {
                    _localReceipt.value = it
                }
        }
    }

    suspend fun deleteReceipt(localReceipt: LocalReceipt) {
        job?.cancel()
        deleteReceiptUseCase.invoke(localReceipt)
    }

}
