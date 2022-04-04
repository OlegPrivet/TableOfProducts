package ru.application.producttable.domain.common

sealed class Resource <out T> (val status : Status, val _data : T?, val message: String?) {

    data class Default<out R>(val data : R) : Resource<R>(
        status = Status.DEFAULT,
        _data = data,
        message = null
    )
    data class GetReceiptSuccess<out R>(val data : R) : Resource<R>(
        status = Status.GET_RECEIPT_SUCCESS,
        _data = data,
        message = null
    )
    data class Loading(val isLoading : Boolean) : Resource<Nothing>(
        status = Status.LOADING,
        _data = null,
        message = null
    )
    data class Error(val exception: String) : Resource<Nothing>(
        status = Status.ERROR,
        _data = null,
        message = exception
    )

}
