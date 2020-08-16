package com.lobo.repogit.data.mapper

import com.lobo.repogit.core.platform.BaseMapper
import com.lobo.repogit.data.model.ItemResponse
import com.lobo.repogit.data.model.OwnerResponse
import com.lobo.repogit.data.model.RepoInformationResponse
import com.lobo.repogit.domain.model.ItemDomain
import com.lobo.repogit.domain.model.OwnerDomain
import com.lobo.repogit.domain.model.RepoInformationDomain

object TopRepoResponseMapper : BaseMapper<RepoInformationDomain, RepoInformationResponse>() {

    override fun transformFrom(source: RepoInformationResponse) =
        RepoInformationDomain(getItemsDomain(source.items))

    override fun transformTo(source: RepoInformationDomain): RepoInformationResponse {
        TODO("Not yet implemented")
    }

    private fun getItemsDomain(itemResponseList: List<ItemResponse>) =

        itemResponseList.flatMap {
            listOf(
                ItemDomain(
                    it.forksCount,
                    it.stargazersCount,
                    it.name,
                    getOwnerDomain(it.owner)
                )
            )
        }

    private fun getOwnerDomain(ownerResponse: OwnerResponse) =
        OwnerDomain(
            ownerResponse.avatarUrl,
            ownerResponse.login
        )

}