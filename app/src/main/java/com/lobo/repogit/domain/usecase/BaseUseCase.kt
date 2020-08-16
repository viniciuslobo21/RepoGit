package com.lobo.repogit.domain.usecase

import kotlinx.coroutines.flow.Flow

abstract class BaseUseCase<out Type, in Params> {
    abstract fun run(params: Params): Flow<Type>

    operator fun invoke(params: Params): Flow<Type> {
        return run(params)
    }

    object None
}