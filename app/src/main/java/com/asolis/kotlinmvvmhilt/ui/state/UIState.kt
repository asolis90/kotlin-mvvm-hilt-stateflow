package com.asolis.kotlinmvvmhilt.ui.state

data class UIState<T>(
    val status: Status = Status.LOADING,
    val data: T? = null,
    val isLoading: Boolean = false,
    val total_count: Int = 0,
    val error: Nothing? = null
)

enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}