package ru.application.producttable.domain.usecases

import ru.application.producttable.domain.repositories.ReceiptRepository

class GetReceiptsByMonthUseCase(private val receiptRepository: ReceiptRepository) {
    suspend operator fun invoke(timestamp: Long) = receiptRepository.getReceiptsByMonth(timestamp)
}
