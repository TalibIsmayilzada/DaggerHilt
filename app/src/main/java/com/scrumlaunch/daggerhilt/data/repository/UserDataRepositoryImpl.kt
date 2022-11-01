package com.scrumlaunch.daggerhilt.data.repository

import com.scrumlaunch.daggerhilt.domain.repository.UserDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDataRepositoryImpl @Inject constructor() : UserDataRepository {

    private var name: String? = null

    override fun setName(name: String) {
        this.name = name
    }

    override fun getName(): String? = name
}