package com.scrumlaunch.daggerhilt.init

import android.content.Context
import androidx.startup.Initializer
import com.scrumlaunch.daggerhilt.BuildConfig
import com.scrumlaunch.daggerhilt.reporting.TimberReleaseTree
import timber.log.Timber

class TimberInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } else {
            Timber.plant(TimberReleaseTree())
        }
        Timber.d("Initialization of Timber completed")
        return
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}