package ru.application.producttable

import android.app.Application
import ru.application.producttable.data.repositories.ReceiptRepositoryImpl
import ru.application.producttable.di.ServiceLocator
import ru.application.producttable.domain.usecases.*

class ReceiptApp : Application() {

    private val receiptRepository: ReceiptRepositoryImpl
        get() = ServiceLocator.provideBooksRepository(this)

    val getReceiptByQRUseCase: GetReceiptByQRUseCase
        get() = GetReceiptByQRUseCase(receiptRepository)

    val getReceiptsUseCase: GetReceiptsUseCase
        get() = GetReceiptsUseCase(receiptRepository)

    val getReceiptsByMonthUseCase: GetReceiptsByMonthUseCase
        get() = GetReceiptsByMonthUseCase(receiptRepository)

    val getLocalReceiptUseCase: GetLocalReceiptUseCase
        get() = GetLocalReceiptUseCase(receiptRepository)

    val saveReceiptUseCase: SaveReceiptUseCase
        get() = SaveReceiptUseCase(receiptRepository)

    val deleteReceiptUseCase: DeleteReceiptUseCase
        get() = DeleteReceiptUseCase(receiptRepository)

}
