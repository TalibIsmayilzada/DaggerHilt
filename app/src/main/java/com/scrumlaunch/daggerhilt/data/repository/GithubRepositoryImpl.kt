package com.scrumlaunch.daggerhilt.data.repository

import android.content.Context
import com.scrumlaunch.daggerhilt.BuildConfig
import com.scrumlaunch.daggerhilt.data.remote.GithubAPI
import com.scrumlaunch.daggerhilt.domain.model.SearchListing
import com.scrumlaunch.daggerhilt.domain.repository.GithubRepository
import com.scrumlaunch.daggerhilt.util.ResultWrapper
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val api: GithubAPI
) : BaseRepository(context), GithubRepository {

    override suspend fun searchRepositories(
        query: String,
        page: Int,
        sort: String
    ): ResultWrapper<Response<SearchListing>> {
        return handleNetwork { api.search(q = query, sort = sort, page = page, token = BuildConfig.ACCESS_TOKEN) }
    }
}