package ru.application.producttable.data.repositories

import kotlinx.coroutines.flow.Flow
import ru.application.producttable.data.repositories.local.ReceiptLocalDataSource
import ru.application.producttable.data.repositories.remote.ReceiptRemoteDataSource
import ru.application.producttable.domain.entities.local.LocalReceipt
import ru.application.producttable.domain.repositories.ReceiptRepository

class ReceiptRepositoryImpl(
    private val remoteDataSource: ReceiptRemoteDataSource,
    private val localDataSource: ReceiptLocalDataSource
) : ReceiptRepository {

    // Remote
    override suspend fun getReceipt(qrraw: String, token: String, target: String): Flow<LocalReceipt> = remoteDataSource.getReceipt(qrraw, token, target)

    // Local
    override suspend fun getReceipts() = localDataSource.getTickets()
    override suspend fun getReceiptsByMonth(timestamp: Long) = localDataSource.getTicketsByMonth(timestamp)
    override suspend fun getReceipt(id: String) = localDataSource.getTicket(id)
    override suspend fun saveReceipt(localReceipt: LocalReceipt) = localDataSource.saveTicket(localReceipt)
    override suspend fun deleteReceipt(localReceipt: LocalReceipt) = localDataSource.deleteTicket(localReceipt)

}
