package com.scrumlaunch.daggerhilt.domain.usecase

import com.scrumlaunch.daggerhilt.domain.model.SearchListing
import com.scrumlaunch.daggerhilt.domain.repository.GithubRepository
import com.scrumlaunch.daggerhilt.util.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class SearchRepositoriesUseCase @Inject constructor(private val repository: GithubRepository) {

    operator fun invoke(
        query: String,
        page: Int,
        sort: String
    ): Flow<ResultWrapper<Response<SearchListing>>> =
        flow {
            emit(ResultWrapper.Loading())
            val repositories = repository.searchRepositories(query, page, sort)
            emit(repositories)
        }
}