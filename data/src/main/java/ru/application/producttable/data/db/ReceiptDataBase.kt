package ru.application.producttable.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.application.producttable.data.entities.local.ReceiptEntity

@Database(entities = [ReceiptEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class ReceiptDataBase : RoomDatabase() {

    abstract fun ticketDao(): ReceiptDao

    companion object {
        @Volatile
        private var INSTANCE: ReceiptDataBase? = null

        fun getDatabase(appContext: Context): ReceiptDataBase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext, ReceiptDataBase::class.java,
                    ReceiptDataBase::class.simpleName!!
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                return instance
            }
        }
    }

}
