package ru.application.producttable.di

import android.content.Context
import kotlinx.coroutines.Dispatchers
import ru.application.producttable.data.api.Network
import ru.application.producttable.data.constants.Constants.BASE_URL
import ru.application.producttable.data.db.ReceiptDataBase
import ru.application.producttable.data.repositories.ReceiptRepositoryImpl
import ru.application.producttable.data.repositories.local.ReceiptLocalDataSource
import ru.application.producttable.data.repositories.local.ReceiptLocalDataSourceImpl
import ru.application.producttable.data.repositories.remote.ReceiptRemoteDataSourceImpl

object ServiceLocator {
    private var database: ReceiptDataBase? = null
    private val networkModule by lazy {
        Network()
    }

    @Volatile
    var booksRepository: ReceiptRepositoryImpl? = null

    fun provideBooksRepository(context: Context): ReceiptRepositoryImpl {
        synchronized(this) {
            return booksRepository ?: createBooksRepository(context)
        }
    }

    private fun createBooksRepository(context: Context): ReceiptRepositoryImpl {
        val newRepo =
            ReceiptRepositoryImpl(
                ReceiptRemoteDataSourceImpl(
                    networkModule.createReceiptApi(BASE_URL),
                ),
                createTicketLocalDataSource(context),
            )
        booksRepository = newRepo
        return newRepo
    }

    private fun createTicketLocalDataSource(context: Context): ReceiptLocalDataSource {
        val database = database ?: createDataBase(context)
        return ReceiptLocalDataSourceImpl(
            database.ticketDao(),
            Dispatchers.IO,
        )
    }

    private fun createDataBase(context: Context): ReceiptDataBase {
        val result = ReceiptDataBase.getDatabase(context)
        database = result
        return result
    }
}
