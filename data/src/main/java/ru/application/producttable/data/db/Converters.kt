package ru.application.producttable.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import ru.application.producttable.domain.entities.local.LocalItem
import java.lang.reflect.Type

class Converters {
    @TypeConverter
    fun fromString(value: String): List<LocalItem> {
        val listType: Type = object : TypeToken<List<LocalItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<LocalItem>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}
