package ru.application.producttable.domain.usecases

import ru.application.producttable.domain.repositories.ReceiptRepository

class GetReceiptsUseCase(private val receiptRepository: ReceiptRepository) {
    suspend operator fun invoke() = receiptRepository.getReceipts()
}
