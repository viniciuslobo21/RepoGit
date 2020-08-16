package com.lobo.repogit.domain.repository

import com.lobo.repogit.domain.model.RepoInformationDomain
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getTopRepos(pageIndex: Int): Flow<RepoInformationDomain>
}