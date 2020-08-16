package com.lobo.repogit.data.repository

import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.data.mapper.TopRepoResponseMapper
import com.lobo.repogit.data.model.ItemResponse
import com.lobo.repogit.data.model.OwnerResponse
import com.lobo.repogit.data.model.RepoInformationResponse
import com.lobo.repogit.data.service.HomeService
import com.lobo.repogit.domain.repository.HomeRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class HomeRepositoryImplTest {

    companion object {
        const val PAGE = 1
    }

    @MockK
    private lateinit var homeService: HomeService
    private lateinit var repository: HomeRepository

    val success =
        RepoInformationResponse(
            listOf(
                ItemResponse(
                    RepoGitConstants.NO_VALUE_INT,
                    RepoGitConstants.NO_VALUE_INT,
                    RepoGitConstants.NO_VALUE_STRING,
                    OwnerResponse(
                        RepoGitConstants.NO_VALUE_STRING,
                        RepoGitConstants.NO_VALUE_STRING
                    )
                )
            )
        )

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        repository = HomeRepositoryImpl(homeService)
    }

    @Test
    fun `repository should call service`() = runBlockingTest {

        coEvery {
            homeService.getTopRepo(PAGE)
        } returns CompletableDeferred(
            Response.success(success)
        )

        homeService.getTopRepo(PAGE)

        verify {
            homeService.getTopRepo(PAGE)
        }
    }

    @Test
    fun `repository call service whith success`() = runBlockingTest {

        coEvery {
            homeService.getTopRepo(PAGE)
        } returns CompletableDeferred(
            Response.success(success)
        )

        repository.getTopRepos(PAGE).collect {
            assertEquals(
                TopRepoResponseMapper.transformFrom(success), it
            )
        }
    }
}