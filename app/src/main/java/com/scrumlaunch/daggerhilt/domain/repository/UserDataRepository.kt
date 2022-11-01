package com.scrumlaunch.daggerhilt.domain.repository

interface UserDataRepository {
    fun setName(name: String)
    fun getName(): String?
}