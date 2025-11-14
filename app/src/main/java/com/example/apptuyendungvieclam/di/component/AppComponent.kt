package com.example.apptuyendungvieclam.di.component

import android.app.Application
import android.content.Context
import com.example.apptuyendungvieclam.common.MVVMApplication
import com.example.apptuyendungvieclam.data.local.AppDatabase
import com.example.apptuyendungvieclam.data.remote.InteractCommon
import com.example.apptuyendungvieclam.di.builder.ActivityBuilderModule
import com.example.apptuyendungvieclam.di.builder.FragmentBuilderModule
import com.example.apptuyendungvieclam.di.model.AppModel
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import java.util.concurrent.Executor
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AndroidInjectionModule::class,AppModel::class,AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        FragmentBuilderModule::class
    ])
interface AppComponent : AndroidInjector<DaggerApplication> {
    fun inject(application: MVVMApplication)
    fun context() : Context
    fun appDatabase(): AppDatabase
    fun interactCommon(): InteractCommon
    fun schedule(): Executor

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application) : Builder

        fun build() : AppComponent
    }
}