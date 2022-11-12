package com.rahi.donut.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.model.ToppingsDetailsModel

/**
 * Created by Rahi on 11/November/2022
 */
@Database(
    entities = [DonutListModel::class, ToppingsDetailsModel::class], version = 3, exportSchema = false
)
abstract class DonutDatabase : RoomDatabase() {
    abstract fun donutDao(): DonutDao
}