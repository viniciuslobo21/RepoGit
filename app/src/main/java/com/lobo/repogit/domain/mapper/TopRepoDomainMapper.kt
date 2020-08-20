package com.lobo.repogit.domain.mapper

import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.core.platform.BaseMapper
import com.lobo.repogit.domain.model.ItemDomain
import com.lobo.repogit.domain.model.OwnerDomain
import com.lobo.repogit.domain.model.RepoInformationDomain
import com.lobo.repogit.presentation.model.ItemPresentation
import com.lobo.repogit.presentation.model.OwnerPresentation
import com.lobo.repogit.presentation.model.RepoInformationPresentation

object TopRepoDomainMapper : BaseMapper<RepoInformationPresentation, RepoInformationDomain>() {

    override fun transformFrom(source: RepoInformationDomain) =
        RepoInformationPresentation(getItemsPresentation(source.items))

    override fun transformTo(source: RepoInformationPresentation): RepoInformationDomain {
        TODO("Not yet implemented")
    }


    private fun getItemsPresentation(itemDomainList: List<ItemDomain>) =

        itemDomainList.flatMap {
            listOf(
                ItemPresentation(
                    it.forksCount,
                    it.stargazersCount,
                    it.name,
                    getOwnerPresentation(it.owner),
                    it.description ?: RepoGitConstants.NO_VALUE_STRING
                )
            )
        }

    private fun getOwnerPresentation(ownerDomain: OwnerDomain) =
        OwnerPresentation(
            ownerDomain.avatarUrl,
            ownerDomain.login
        )

}