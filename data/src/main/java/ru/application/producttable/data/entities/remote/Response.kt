package ru.application.producttable.data.entities.remote

import com.google.gson.annotations.SerializedName
import ru.application.producttable.data.utils.dateToLong
import ru.application.producttable.data.utils.getDate
import ru.application.producttable.data.utils.toAmount
import ru.application.producttable.domain.entities.local.LocalReceipt

data class Response(

    @SerializedName("code")
    val code: Int,

    @SerializedName("data")
    val successfulData: SuccessfulData?
)

fun Response.toLocalReceipt(target: String) = LocalReceipt(
    id = successfulData?.json?.fiscalDocumentNumber.toString(),
    user = successfulData?.json?.user ?: "",
    timestamp = successfulData?.json?.dateTime?.dateToLong() ?: 0L,
    totalSum = successfulData?.json?.totalSum?.toAmount() ?: "0",
    items = successfulData?.json?.items?.toLocalItemList(
        successfulData.json.user,
        successfulData.json.dateTime.dateToLong().getDate()
    ) ?: listOf(),
    target = target,
)
