package com.scrumlaunch.daggerhilt.di

import android.content.Context
import com.scrumlaunch.daggerhilt.domain.repository.UserDataRepository
import com.scrumlaunch.daggerhilt.init.BackgroundCheckInitializer
import com.scrumlaunch.daggerhilt.workmanager.CounterWorkManager
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import java.lang.IllegalStateException

@EntryPoint
@InstallIn(SingletonComponent::class)
interface InitializerEntryPoint {

    companion object {

        fun resolve(context: Context): InitializerEntryPoint {
            val appContext = context.applicationContext ?: throw IllegalStateException()
            return EntryPointAccessors.fromApplication(
                appContext,
                InitializerEntryPoint::class.java
            )
        }
    }

    fun inject(backgroundCheckInitializer: BackgroundCheckInitializer)
    fun inject(counterWorkManager: CounterWorkManager)
}