package ru.application.producttable.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import ru.application.producttable.data.entities.local.ReceiptEntity

@Dao
interface ReceiptDao {

    @Query("SELECT * FROM tickets")
    fun getTickets() : Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM tickets where timestamp = (:timestamp)")
    fun getTicketsByMonth(timestamp: Long) : Flow<List<ReceiptEntity>>

    @Query("SELECT * FROM tickets where id = (:id)")
    fun getTicket(id: String) : Flow<ReceiptEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTicket(receipt: ReceiptEntity)

    @Delete
    suspend fun deleteTicket(receipt: ReceiptEntity)
}
