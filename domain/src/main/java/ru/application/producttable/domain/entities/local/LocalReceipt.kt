package ru.application.producttable.domain.entities.local

data class LocalReceipt(
    val id: String,
    val timestamp: Long,
    val totalSum: String,
    val items: List<LocalItem>,
    val user: String,
    val target: String,
) {
    constructor() : this(
        "", 0L, "", listOf(),"", ""
    )
}

