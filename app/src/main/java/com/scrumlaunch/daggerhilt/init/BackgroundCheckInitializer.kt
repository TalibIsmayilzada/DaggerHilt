package com.scrumlaunch.daggerhilt.init

import android.content.Context
import androidx.startup.Initializer
import com.scrumlaunch.daggerhilt.di.InitializerEntryPoint
import com.scrumlaunch.daggerhilt.domain.repository.UserDataRepository
import timber.log.Timber
import javax.inject.Inject

class BackgroundCheckInitializer : Initializer<Unit> {

    @Inject
    lateinit var userDataRepository: UserDataRepository

    override fun create(context: Context) {
        InitializerEntryPoint.resolve(context).inject(this@BackgroundCheckInitializer)
        userDataRepository.getName()
        Timber.d("Initialization of Background check completed")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}