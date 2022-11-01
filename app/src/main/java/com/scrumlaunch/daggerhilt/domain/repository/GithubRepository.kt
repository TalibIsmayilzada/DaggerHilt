package com.scrumlaunch.daggerhilt.domain.repository

import com.scrumlaunch.daggerhilt.domain.model.SearchListing
import com.scrumlaunch.daggerhilt.util.ResultWrapper
import retrofit2.Response

interface GithubRepository {

    suspend fun searchRepositories(
        query: String,
        page: Int,
        sort: String
    ): ResultWrapper<Response<SearchListing>>

}