package com.lobo.repogit.data.model

import com.google.gson.annotations.SerializedName

data class ItemResponse(
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val name: String,
    val owner: OwnerResponse
)