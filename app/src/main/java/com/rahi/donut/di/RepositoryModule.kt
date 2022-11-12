package com.rahi.donut.di

import com.rahi.donut.data.repository.DonutRepositoryImpl
import com.rahi.donut.domain.repository.DonutRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by Rahi on 11/November/2022
 */
@InstallIn(ViewModelComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideDonutRepository(repository: DonutRepositoryImpl): DonutRepository
}