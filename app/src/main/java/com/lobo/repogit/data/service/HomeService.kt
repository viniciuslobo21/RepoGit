package com.lobo.repogit.data.service

import com.lobo.repogit.data.model.RepoInformationResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {

    @GET("search/repositories?q=language:kotlin&sort=stars")
    fun getTopRepo(
        @Query("page") pageIndex: Int
    ): Deferred<Response<RepoInformationResponse>>
}