package ru.application.producttable.data.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import ru.application.producttable.data.entities.remote.Response

interface ReceiptApi {

    @FormUrlEncoded
    @Headers("Content-type: application/x-www-form-urlencoded",
        "Accept: */*")
    @POST("api/v1/check/get")
    suspend fun getReceipt(
        @Field("qrraw") qrraw: String,
        @Field("token") token: String,
    ): Response

}
