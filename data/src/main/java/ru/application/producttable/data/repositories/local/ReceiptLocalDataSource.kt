package ru.application.producttable.data.repositories.local

import kotlinx.coroutines.flow.Flow
import ru.application.producttable.domain.entities.local.LocalReceipt

interface ReceiptLocalDataSource {
    suspend fun getTickets() : Flow<List<LocalReceipt>>
    suspend fun getTicketsByMonth(timestamp: Long) : Flow<List<LocalReceipt>>
    suspend fun getTicket(id: String) : Flow<LocalReceipt>
    suspend fun saveTicket(localReceipt: LocalReceipt)
    suspend fun deleteTicket(localReceipt: LocalReceipt)
}
