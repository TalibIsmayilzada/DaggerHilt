package com.scrumlaunch.daggerhilt.domain.model

import com.google.gson.annotations.SerializedName

data class SearchListing(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = true,
    @SerializedName("items")
    val items: List<Item>? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null
) : BaseResponse()
