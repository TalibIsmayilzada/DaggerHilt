package com.scrumlaunch.daggerhilt.domain.model

import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status")
    val status: String? = null
}