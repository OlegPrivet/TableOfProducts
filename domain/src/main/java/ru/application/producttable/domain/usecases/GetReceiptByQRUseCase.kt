package ru.application.producttable.domain.usecases

import ru.application.producttable.domain.repositories.ReceiptRepository

class GetReceiptByQRUseCase(private val receiptRepository: ReceiptRepository) {
    suspend operator fun invoke(qrraw: String, token: String, target: String) = receiptRepository.getReceipt(qrraw, token, target)
}
