package com.example.apptuyendungvieclam.di.model

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.di.builder.ViewModelModule
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
class AppModel {
    @Provides
    @Singleton
    internal fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "mvvm-database").allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideInteractCommon(): InteractCommon {
        return InteractCommon()
    }

    @Provides
    @Singleton
    internal fun provideSchedule(): Executor {
        return Executors.newFixedThreadPool(4)
    }
}