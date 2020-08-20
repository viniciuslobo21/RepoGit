package com.lobo.repogit.domain.model

import com.lobo.repogit.core.RepoGitConstants

data class ItemDomain(
    val forksCount: Int,
    val stargazersCount: Int,
    val name: String,
    val owner: OwnerDomain,
    val description: String? = RepoGitConstants.NO_VALUE_STRING
)