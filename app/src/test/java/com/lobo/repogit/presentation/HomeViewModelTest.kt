package com.lobo.repogit.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lobo.repogit.core.RepoGitConstants
import com.lobo.repogit.core.extension.getOrAwaitValue
import com.lobo.repogit.core.platform.Response
import com.lobo.repogit.domain.interactor.GetTopReposUseCase
import com.lobo.repogit.domain.model.ItemDomain
import com.lobo.repogit.domain.model.OwnerDomain
import com.lobo.repogit.domain.model.RepoInformationDomain
import com.lobo.repogit.presentation.home.HomeViewModel
import com.lobo.repogit.presentation.model.ItemPresentation
import com.lobo.repogit.presentation.model.OwnerPresentation
import com.lobo.repogit.presentation.model.RepoInformationPresentation
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any

class HomeViewModelTest {

    companion object {
        const val PAGE = 1
    }

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: HomeViewModel

    private val response = getResponseMock()

    private val domain = getDomainMock()

    @MockK
    private lateinit var getTopReposUseCase: GetTopReposUseCase

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
        MockKAnnotations.init(this)
        viewModel = HomeViewModel(getTopReposUseCase)
    }

    @ExperimentalCoroutinesApi
    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    private fun getResponseMock() =
        RepoInformationPresentation(
            listOf(
                ItemPresentation(
                    RepoGitConstants.NO_VALUE_INT,
                    RepoGitConstants.NO_VALUE_INT,
                    RepoGitConstants.NO_VALUE_STRING,
                    OwnerPresentation(
                        RepoGitConstants.NO_VALUE_STRING,
                        RepoGitConstants.NO_VALUE_STRING
                    )
                )
            )
        )

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

    @Test
    fun `get top repos success`() = runBlockingTest {
        every {
            getTopReposUseCase(any())
        } returns flow {
            emit(domain)
        }

        viewModel.getTopRepos(1)

        verify {
            getTopReposUseCase(any())
        }

        assertEquals(
            viewModel.topRepoLiveData.getOrAwaitValue(),
            Response.Success(response)
        )
    }

    @Test
    fun `get top repos fail`() = runBlockingTest {

        val expectedException = Throwable()

        every {
            getTopReposUseCase(any())
        } returns flow {
            throw expectedException
        }

        viewModel.getTopRepos(page = PAGE)

        verify {
            getTopReposUseCase(any())
        }

        assertEquals(
            viewModel.topRepoLiveData.getOrAwaitValue(),
            Response.Failure(expectedException)
        )
    }

}