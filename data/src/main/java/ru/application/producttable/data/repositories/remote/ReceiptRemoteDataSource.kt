package ru.application.producttable.data.repositories.remote

import kotlinx.coroutines.flow.Flow
import ru.application.producttable.domain.entities.local.LocalReceipt

interface ReceiptRemoteDataSource {

    suspend fun getReceipt(qrraw: String, token: String, target: String): Flow<LocalReceipt>
}
