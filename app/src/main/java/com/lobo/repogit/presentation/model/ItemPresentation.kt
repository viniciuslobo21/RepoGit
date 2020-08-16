package com.lobo.repogit.presentation.model

import com.lobo.repogit.core.RepoGitConstants

data class ItemPresentation(

    val forksCount: Int = RepoGitConstants.NO_VALUE_INT,
    val stargazersCount: Int = RepoGitConstants.NO_VALUE_INT,
    val name: String = RepoGitConstants.NO_VALUE_STRING,
    val owner: OwnerPresentation = OwnerPresentation()
)