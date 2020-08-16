package com.lobo.repogit.presentation.model

import com.lobo.repogit.core.RepoGitConstants

data class OwnerPresentation(
    val avatar_url: String = RepoGitConstants.NO_VALUE_STRING,
    val login: String = RepoGitConstants.NO_VALUE_STRING
)