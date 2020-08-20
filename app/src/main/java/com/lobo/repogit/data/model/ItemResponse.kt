package com.lobo.repogit.data.model

import com.google.gson.annotations.SerializedName
import com.lobo.repogit.core.RepoGitConstants

data class ItemResponse(
    @SerializedName("forks_count")
    val forksCount: Int,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
    val name: String = RepoGitConstants.NO_VALUE_STRING,
    val owner: OwnerResponse,
    @SerializedName("description")
    val description: String? = RepoGitConstants.NO_VALUE_STRING
)