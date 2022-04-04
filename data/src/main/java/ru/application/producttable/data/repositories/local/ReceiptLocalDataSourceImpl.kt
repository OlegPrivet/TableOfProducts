package ru.application.producttable.data.repositories.local

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.application.producttable.data.db.ReceiptDao
import ru.application.producttable.data.entities.local.toLocalEntity
import ru.application.producttable.data.entities.local.toReceiptEntity
import ru.application.producttable.domain.entities.local.LocalReceipt

class ReceiptLocalDataSourceImpl(
    private val receiptDao: ReceiptDao,
    private val dispatcher: CoroutineDispatcher,
) : ReceiptLocalDataSource {

    override suspend fun getTickets(): Flow<List<LocalReceipt>> {
        return receiptDao.getTickets().map { list ->
            list.map { ticket ->
                ticket.toLocalEntity()
            }
        }
    }

    override suspend fun getTicketsByMonth(timestamp: Long): Flow<List<LocalReceipt>> {
        return receiptDao.getTicketsByMonth(timestamp).map { list ->
            list.map { ticket ->
                ticket.toLocalEntity()
            }
        }
    }

    override suspend fun getTicket(id: String): Flow<LocalReceipt> {
        return receiptDao.getTicket(id).map { ticket ->
            ticket.toLocalEntity()
        }
    }

    override suspend fun saveTicket(localReceipt: LocalReceipt) = withContext(dispatcher){
        receiptDao.saveTicket(localReceipt.toReceiptEntity())
    }

    override suspend fun deleteTicket(localReceipt: LocalReceipt) = withContext(dispatcher){
        receiptDao.deleteTicket(localReceipt.toReceiptEntity())
    }
}
