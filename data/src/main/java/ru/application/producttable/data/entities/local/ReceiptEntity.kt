package ru.application.producttable.data.entities.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.application.producttable.domain.entities.local.LocalItem
import ru.application.producttable.domain.entities.local.LocalReceipt

@Entity(tableName = "tickets")
data class ReceiptEntity(
    @PrimaryKey val id: String,
    val timestamp: Long,
    val totalSum: String,
    val items: List<LocalItem>,
    val user: String,
    val target: String,
)

fun LocalReceipt.toReceiptEntity() = ReceiptEntity(
    id = id,
    user = user,
    timestamp = timestamp,
    totalSum = totalSum,
    items = items,
    target = target,
)

fun ReceiptEntity.toLocalEntity() = LocalReceipt(
    id = id,
    user = user,
    timestamp = timestamp,
    totalSum = totalSum,
    items = items,
    target = target,
)
