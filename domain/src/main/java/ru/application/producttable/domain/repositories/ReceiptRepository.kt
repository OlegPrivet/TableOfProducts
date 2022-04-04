package ru.application.producttable.domain.repositories

import kotlinx.coroutines.flow.Flow
import ru.application.producttable.domain.entities.local.LocalReceipt

interface ReceiptRepository {

    // Remote
    suspend fun getReceipt(qrraw: String, token: String, target: String): Flow<LocalReceipt>

    // Local
    suspend fun getReceipts() : Flow<List<LocalReceipt>>
    suspend fun getReceiptsByMonth(timestamp: Long) : Flow<List<LocalReceipt>>
    suspend fun getReceipt(id: String) : Flow<LocalReceipt>
    suspend fun saveReceipt(localReceipt: LocalReceipt)
    suspend fun deleteReceipt(localReceipt: LocalReceipt)

}
