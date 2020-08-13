package com.lobo.repogit.core.handlers

import com.lobo.repogit.domain.usecase.BaseUseCase

object UseCaseHandler {
    suspend fun <RV, T> execute(useCase: BaseUseCase<RV, T>, values: RV? = null): T {
        if (values != null) {
            useCase.setRequestValues(values)
        }
        return useCase.run()
    }
}