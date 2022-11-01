package com.scrumlaunch.daggerhilt.data.remote

import com.scrumlaunch.daggerhilt.domain.model.SearchListing
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface GithubAPI {

    companion object{
        const val DEFAULT_RESULT_PER_PAGE = 30
    }

    @Headers("Accept: application/vnd.github.v3+json")
    @GET("search/repositories")
    suspend fun search(
        @Query("q") q: String,
        @Query("sort") sort: String,
        @Query("per_page") perPage: Int = DEFAULT_RESULT_PER_PAGE,
        @Query("page") page: Int,
        @Header("Authorization") token: String
    ): Response<SearchListing>
}