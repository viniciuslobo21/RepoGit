package com.lobo.repogit.domain.usecase

abstract class BaseUseCase<in RV, T> {
    private var requestValue: RV? = null

    fun setRequestValues(requestValues: RV?) {
        this.requestValue = requestValues
    }

    suspend fun run(): T {
        return executeUseCase(requestValue)
    }

    abstract suspend fun executeUseCase(requestValues: RV? = null): T
}
