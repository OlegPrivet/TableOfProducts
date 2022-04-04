package ru.application.producttable.presentation.fragments.receiptdatafragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import ru.application.producttable.data.BuildConfig
import ru.application.producttable.domain.common.Resource
import ru.application.producttable.domain.entities.local.LocalReceipt
import ru.application.producttable.domain.usecases.GetReceiptByQRUseCase
import ru.application.producttable.domain.usecases.SaveReceiptUseCase

class ReceiptFetchDataVM(
    private val getReceiptByQRUseCase: GetReceiptByQRUseCase,
    private val saveReceiptUseCase: SaveReceiptUseCase,
) : ViewModel() {


    private val _result = MutableStateFlow<Resource<LocalReceipt>>(Resource.Default(LocalReceipt()))
    val result: StateFlow<Resource<LocalReceipt>> = _result

    private var qrCode: String = ""

    fun getReceipt(qr: String, target: String) {
        qrCode = qr
        viewModelScope.launch {
            getReceiptByQRUseCase.invoke(qr, BuildConfig.ACCESS_TOKEN, target)
                .onStart {
                    _result.value = Resource.Loading(true)
                }
                .catch {
                    it.message?.let { message ->
                        _result.value = Resource.Error(message)
                    }
                }
                .collect { response ->
                    _result.value = Resource.GetReceiptSuccess(response)
                }
        }
    }

    fun saveReceipt(localReceipt: LocalReceipt){
        viewModelScope.launch {
            saveReceiptUseCase.invoke(localReceipt)
        }
    }
}
