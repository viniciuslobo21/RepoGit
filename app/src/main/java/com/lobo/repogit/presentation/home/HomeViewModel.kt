package com.lobo.repogit.presentation.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lobo.repogit.core.platform.BaseViewModel
import com.lobo.repogit.core.platform.Response
import com.lobo.repogit.domain.interactor.GetTopReposUseCase
import com.lobo.repogit.domain.mapper.TopRepoDomainMapper
import com.lobo.repogit.presentation.model.RepoInformationPresentation
import kotlinx.coroutines.flow.*

class HomeViewModel(private val getTopReposUseCase: GetTopReposUseCase) : BaseViewModel() {

    val topRepoLiveData = MutableLiveData<Response<RepoInformationPresentation>>()

    fun getTopRepos(page: Int) {
        getTopReposUseCase(GetTopReposUseCase.Params(page))
            .onStart { loading.value = true }
            .onCompletion { loading.value = false }
            .map {
                topRepoLiveData.value = Response.Success(TopRepoDomainMapper.transformFrom(it))
            }
            .catch {
                topRepoLiveData.value = Response.Failure(it)
            }
            .launchIn(viewModelScope)
    }
}