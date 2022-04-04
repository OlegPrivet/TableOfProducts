package ru.application.producttable.domain.usecases

import ru.application.producttable.domain.entities.local.LocalReceipt
import ru.application.producttable.domain.repositories.ReceiptRepository

class DeleteReceiptUseCase(private val receiptRepository: ReceiptRepository) {
    suspend operator fun invoke(localReceipt: LocalReceipt) = receiptRepository.deleteReceipt(localReceipt)
}
