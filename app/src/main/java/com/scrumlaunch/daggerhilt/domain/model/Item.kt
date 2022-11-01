package com.scrumlaunch.daggerhilt.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("forks_count")
    val forksCount: Int? = null,
    @SerializedName("full_name")
    val fullName: String? = null,
    @SerializedName("html_url")
    val htmlUrl: String? = null,
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("language")
    val language: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("owner")
    val owner: Owner? = null,
    @SerializedName("pushed_at")
    val pushedAt: String? = null,
    @SerializedName("size")
    val size: Int? = null,
    @SerializedName("topics")
    val topics: List<String>? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("watchers")
    val watchers: Int? = null,
    @SerializedName("watchers_count")
    val watchersCount: Int? = null,
    @SerializedName("open_issues_count")
    val openIssuesCount: Int? = null
): Parcelable