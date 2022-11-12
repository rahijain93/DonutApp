package com.rahi.donut.di

import com.rahi.donut.data.remote.DonutApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {

    @Provides
    fun provideDonutApi(retrofit: Retrofit): DonutApi = retrofit.create(DonutApi::class.java)
}