package com.lobo.repogit.domain.model

import com.google.gson.annotations.SerializedName

data class ItemDomain(
    @SerializedName("forks_count")
    val forksCount: Int,
    val stargazersCount: Int,
    val name: String,
    val owner: OwnerDomain
)