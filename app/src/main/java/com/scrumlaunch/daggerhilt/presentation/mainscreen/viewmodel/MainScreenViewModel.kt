package com.scrumlaunch.daggerhilt.presentation.mainscreen.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scrumlaunch.daggerhilt.data.remote.GithubAPI
import com.scrumlaunch.daggerhilt.domain.model.SearchListing
import com.scrumlaunch.daggerhilt.domain.usecase.SearchRepositoriesUseCase
import com.scrumlaunch.daggerhilt.util.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val useCase: SearchRepositoriesUseCase
) : ViewModel() {


    private val _data =
        MutableStateFlow<ResultWrapper<Response<SearchListing>>?>(null)
    val data = _data.asStateFlow()

    private val getTotalPageCount get(): Int = totalItemCount / GithubAPI.DEFAULT_RESULT_PER_PAGE

    val getNextPage get() = (getTotalPageCount > currentPage + 1) && !isLoading

    var searchText = EMPTY

    private var currentPage = 1
    private var totalItemCount = 0
    private var isLoading = true

    private var DEFAULT_SORT = SORT_BY_STARS

    fun search(query: String) {
        searchText = query
        useCase.invoke(query, currentPage, DEFAULT_SORT).onEach { result ->
            when (result) {
                is ResultWrapper.Loading -> isLoading = true
                is ResultWrapper.Success -> {
                    isLoading = false
                    result.data?.body()?.totalCount?.let { totalItemCount = it }
                }
                else -> isLoading = false
            }

            _data.emit(result)

        }.launchIn(viewModelScope)
        currentPage += 1
    }

    fun setSortType(type: String) {
        DEFAULT_SORT = type
    }

    fun resetPage(){
        currentPage = 1
        totalItemCount = 0
    }

    companion object {
        const val SORT_BY_STARS = "stars"
        const val SORT_BY_FORKS = "forks"
        const val SORT_BY_UPDATED = "updated"
        const val EMPTY = ""
    }
}