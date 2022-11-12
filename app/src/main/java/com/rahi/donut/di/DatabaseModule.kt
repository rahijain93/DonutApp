package com.rahi.donut.di

import android.content.Context
import androidx.room.Room
import com.rahi.donut.data.local.DonutDao
import com.rahi.donut.data.local.DonutDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by Rahi on 11/November/2022
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideDonutDatabase(
        @ApplicationContext context: Context
    ): DonutDatabase {
        return Room.databaseBuilder(
            context,
            DonutDatabase::class.java,
            "Donut.db"
        )
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }

    @Provides
    fun provideDonutDao(database: DonutDatabase): DonutDao {
        return database.donutDao()
    }
}