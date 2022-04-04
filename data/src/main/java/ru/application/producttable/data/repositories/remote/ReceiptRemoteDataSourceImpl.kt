package ru.application.producttable.data.repositories.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.application.producttable.data.api.ReceiptApi
import ru.application.producttable.data.entities.remote.toLocalReceipt

class ReceiptRemoteDataSourceImpl(
    private val api: ReceiptApi,
) : ReceiptRemoteDataSource {

    override suspend fun getReceipt(qrraw: String, token: String, target: String) = flow {
        val searchResult = api.getReceipt(qrraw, token).toLocalReceipt(target = target)
        emit(searchResult)
    }.flowOn(Dispatchers.IO)

}
