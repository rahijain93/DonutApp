package com.rahi.donut.data.di

import android.app.Application
import com.rahi.donut.AppController
import com.rahi.donut.data.database.DonutsDatabase
import com.rahi.donut.data.repo.DonutRepository
import com.rahi.donut.ui.viewmodel.DonutViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MainVM = module {
    viewModel {
        DonutViewModel(get(), get())
    }
}

val ApplicationModule = module {
    single { AppController.getAppInstance() }
}

val DatabaseModule = module {

    fun provideDatabase(application: Application): DonutsDatabase {
        return  DonutsDatabase.buildDatabase(application)
    }

    single { provideDatabase(androidApplication()) }
}

val NetworkDependency = module {
    single {
        DonutRepository(get(),get())
    }
}