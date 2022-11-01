package com.scrumlaunch.daggerhilt.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.scrumlaunch.daggerhilt.di.InitializerEntryPoint
import com.scrumlaunch.daggerhilt.domain.repository.UserDataRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import timber.log.Timber
import javax.inject.Inject

@HiltWorker
class CounterWorkManager @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParameters: WorkerParameters,
) : Worker(context, workerParameters) {

    @Inject
    lateinit var userDataRepository: UserDataRepository


    init {
        Timber.d("${workerParameters.inputData.keyValueMap}")
    }

    override fun doWork(): Result {
        InitializerEntryPoint.resolve(context).inject(this)
        userDataRepository.setName("Test")
        for (i in 0..20) {
            Timber.d("${userDataRepository.getName()}: $i")
        }
        return Result.success()
    }

}