package com.rahi.donut.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rahi.donut.data.dao.DonutsDao
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.model.ToppingsDetailsModel


@Database(
    entities = [DonutListModel::class,ToppingsDetailsModel::class],version = 3,exportSchema = false
)
abstract class DonutsDatabase : RoomDatabase() {
    companion object {

        @Volatile
        var instance: DonutsDatabase? = null

        private val LOCK = Any()

        const val DATABASE_NAME = "Donut.db"

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        fun buildDatabase(context: Context): DonutsDatabase {
            return Room.databaseBuilder(
                context,
                DonutsDatabase::class.java,
                DATABASE_NAME
            )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
        }
    }

    abstract fun mainDao(): DonutsDao?

}