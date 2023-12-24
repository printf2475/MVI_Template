package com.example.mvi_template.domain.util

/**
 * ViewModel에서 Exception 예외처리를 하지 않고, UseCase 처리하고자 범용적으로
 * 사용하기 위해서 생성된 클래스입니다.
 */
sealed class UseCaseResult<T> {
    data class Success<T>(val data: T) : UseCaseResult<T>()
    data class Error<T>(val exception: Throwable) : UseCaseResult<T>()
}