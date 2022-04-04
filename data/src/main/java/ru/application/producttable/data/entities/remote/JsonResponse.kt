package ru.application.producttable.data.entities.remote

import com.google.gson.annotations.SerializedName

data class JsonResponse(

    @SerializedName("code")
    val code: Int,

    @SerializedName("user")
    val user: String,

    @SerializedName("items")
    val items: List<ReceiptItems>,

    @SerializedName("nds10")
    val nds10: Int,

    @SerializedName("nds18")
    val nds20: Int,

    @SerializedName("fnsUrl")
    val fnsUrl: String,

    @SerializedName("userInn")
    val userInn: String,

    @SerializedName("dateTime")
    val dateTime: String,

    @SerializedName("kktRegId")
    val kktRegId: String,

    @SerializedName("operator")
    val operator: String,

    @SerializedName("totalSum")
    val totalSum: Long,

    @SerializedName("creditSum")
    val creditSum: Long,

    @SerializedName("fiscalSign")
    val fiscalSign: Long,

    @SerializedName("prepaidSum")
    val prepaidSum: Int,

    @SerializedName("properties")
    val properties: Properties,

    @SerializedName("retailPlace")
    val retailPlace: String,

    @SerializedName("shiftNumber")
    val shiftNumber: Int,

    @SerializedName("cashTotalSum")
    val cashTotalSum: Long,

    @SerializedName("provisionSum")
    val provisionSum: Long,

    @SerializedName("taxationType")
    val taxationType: Int,

    @SerializedName("ecashTotalSum")
    val ecashTotalSum: Long,

    @SerializedName("operationType")
    val operationType: Int,

    @SerializedName("requestNumber")
    val requestNumber: Int,

    @SerializedName("sellerAddress")
    val sellerAddress: String,

    @SerializedName("fiscalDriveNumber")
    val fiscalDriveNumber: String,

    @SerializedName("retailPlaceAddress")
    val retailPlaceAddress: String,

    @SerializedName("appliedTaxationType")
    val appliedTaxationType: Int,

    @SerializedName("buyerPhoneOrAddress")
    val buyerPhoneOrAddress: String,

    @SerializedName("fiscalDocumentFormatVer")
    val fiscalDocumentFormatVer: Int,

    @SerializedName("fiscalDocumentNumber")
    val fiscalDocumentNumber: Int,
)
