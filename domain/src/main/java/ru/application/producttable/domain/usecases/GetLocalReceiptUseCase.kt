package ru.application.producttable.domain.usecases

import ru.application.producttable.domain.repositories.ReceiptRepository

class GetLocalReceiptUseCase(private val receiptRepository: ReceiptRepository) {
    suspend operator fun invoke(id: String) = receiptRepository.getReceipt(id)
}
