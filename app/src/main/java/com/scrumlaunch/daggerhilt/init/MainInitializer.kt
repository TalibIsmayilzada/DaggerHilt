package com.scrumlaunch.daggerhilt.init

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

class MainInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        Timber.d("Initialization of Main completed")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf(
        TimberInitializer::class.java,
        BackgroundCheckInitializer::class.java
    )
}