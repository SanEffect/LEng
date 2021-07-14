package com.san.domain.interactor

import com.san.domain.Result

interface UseCase<out Type, in Params> {
    suspend operator fun invoke(params: Params): Result<Type>
}
