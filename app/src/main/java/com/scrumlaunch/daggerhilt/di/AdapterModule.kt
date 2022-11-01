package com.scrumlaunch.daggerhilt.di

import com.scrumlaunch.daggerhilt.presentation.mainscreen.adapter.RepositoriesRVAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Provides
    @Singleton
    fun provideRepositoriesAdapter() = RepositoriesRVAdapter()
}