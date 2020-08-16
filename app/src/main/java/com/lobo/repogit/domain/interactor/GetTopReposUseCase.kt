package com.lobo.repogit.domain.interactor

import com.lobo.repogit.domain.model.RepoInformationDomain
import com.lobo.repogit.domain.repository.HomeRepository
import com.lobo.repogit.domain.usecase.BaseUseCase
import kotlinx.coroutines.flow.Flow

class GetTopReposUseCase(private val homeRepository: HomeRepository) :
    BaseUseCase<RepoInformationDomain, GetTopReposUseCase.Params>() {

    override fun run(params: Params): Flow<RepoInformationDomain> {
        return homeRepository.getTopRepos(params.page)
    }

    data class Params(
        val page: Int
    )
}