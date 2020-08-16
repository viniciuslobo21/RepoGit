package com.lobo.repogit.data.repository

import com.lobo.repogit.data.mapper.TopRepoResponseMapper
import com.lobo.repogit.data.safeAwait
import com.lobo.repogit.data.service.HomeService
import com.lobo.repogit.domain.model.RepoInformationDomain
import com.lobo.repogit.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeRepositoryImpl(private val service: HomeService) : HomeRepository {
    override fun getTopRepos(pageIndex: Int): Flow<RepoInformationDomain> {

        return service.getTopRepo(pageIndex).safeAwait().map {
            TopRepoResponseMapper.transformFrom(it)
        }

    }
}
