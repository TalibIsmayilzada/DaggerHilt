package com.scrumlaunch.daggerhilt.di

import com.scrumlaunch.daggerhilt.data.repository.GithubRepositoryImpl
import com.scrumlaunch.daggerhilt.data.repository.UserDataRepositoryImpl
import com.scrumlaunch.daggerhilt.domain.repository.GithubRepository
import com.scrumlaunch.daggerhilt.domain.repository.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindGithubRepository(githubRepositoryImpl: GithubRepositoryImpl): GithubRepository

    @Binds
    @Singleton
    abstract fun binUserDataRepository(userDataRepositoryImpl: UserDataRepositoryImpl): UserDataRepository
}