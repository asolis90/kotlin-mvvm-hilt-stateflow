package com.asolis.kotlinmvvmhilt.ui.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asolis.kotlinmvvmhilt.data.models.Article
import com.asolis.kotlinmvvmhilt.data.repository.DataRepositoryImpl
import com.asolis.kotlinmvvmhilt.ui.state.Status
import com.asolis.kotlinmvvmhilt.ui.state.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val dataRepositoryImpl: DataRepositoryImpl) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<List<Article>>>(UIState(Status.LOADING))

    val uiState = _uiState.asStateFlow()

    init {
        fetchTopHeadlines()
    }

    private fun fetchTopHeadlines() {
        viewModelScope.launch {
            dataRepositoryImpl.getTopNews("us")
                .catch { e ->
                    _uiState.value = UIState(Status.ERROR)
                }
                .collect {
                    _uiState.value = UIState(Status.SUCCESS, data = it)
                }
        }
    }
}