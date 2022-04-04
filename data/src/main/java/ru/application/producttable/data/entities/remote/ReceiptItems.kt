package ru.application.producttable.data.entities.remote

import com.google.gson.annotations.SerializedName
import ru.application.producttable.data.utils.toAmount
import ru.application.producttable.domain.entities.local.LocalItem

data class ReceiptItems(

    @SerializedName("nds")
    val nds: Int,

    @SerializedName("sum")
    val sum: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("price")
    val price: Long,

    @SerializedName("ndsSum")
    val ndsSum: Int,

    @SerializedName("quantity")
    val quantity: Float,

    @SerializedName("paymentType")
    val paymentType: Int,

    @SerializedName("productType")
    val productType: Int,
)

fun List<ReceiptItems>.toLocalItemList(user: String, date: String): List<LocalItem> {
    val list = mutableListOf<LocalItem>()
    forEach { receiptItems ->
        list.add(receiptItems.toLocalReceipt(user, date))
    }
    return list.toList()
}

fun ReceiptItems.toLocalReceipt(user: String, date: String) = LocalItem(
    user = user,
    date = date,
    name = name,
    quantity = quantity,
    sum = sum.toAmount()
)
