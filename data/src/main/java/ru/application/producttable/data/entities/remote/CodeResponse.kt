package ru.application.producttable.data.entities.remote

enum class CodeResponse(val code: Int, val data: String) {
    INCORRECT_RECEIPT( 0, "Чек некорректен."),
    GET_DATA_SUCCESSFUL( 1, "Данные чека получены."),
    GET_DATA_NOT_SUCCESSFUL( 2, "Данные чека пока не получены."),
    EXCEEDED_NUMBER_REQUEST( 3, "Превышено количество обращений по чеку."),
    WAIT_REQUEST( 4, "Ожидание перед повторным запросом."),
    NOT_GET_DATA( 5, "Данные не получены."),
}
