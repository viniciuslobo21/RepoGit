package com.lobo.repogit.domain.interactor

import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.domain.model.ItemDomain
import com.lobo.repogit.domain.model.OwnerDomain
import com.lobo.repogit.domain.model.RepoInformationDomain
import com.lobo.repogit.domain.repository.HomeRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetTopReposUseCaseTest {

    companion object {
        const val PAGE = 1
    }

    private val topReposDomain = getDomainMock()

    private lateinit var useCase: GetTopReposUseCase

    @MockK
    private lateinit var repository: HomeRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        useCase = GetTopReposUseCase(repository)

        every {
            repository.getTopRepos(PAGE)
        } returns flow {
            emit(topReposDomain)
        }
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `get top repositories with success`() = runBlockingTest {
        useCase.invoke(
            GetTopReposUseCase.Params(PAGE)
        ).collect {
            Assert.assertEquals(topReposDomain, it)
        }
    }

    private fun getDomainMock() =
        RepoInformationDomain(
            listOf(
                ItemDomain(
                    RepoGitConstants.NO_VALUE_INT,
                    RepoGitConstants.NO_VALUE_INT,
                    RepoGitConstants.NO_VALUE_STRING,
                    OwnerDomain(
                        RepoGitConstants.NO_VALUE_STRING,
                        RepoGitConstants.NO_VALUE_STRING
                    )
                )
            )
        )
}