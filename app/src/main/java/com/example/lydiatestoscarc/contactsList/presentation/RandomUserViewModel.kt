package com.example.lydiatestoscarc.contactsList.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lydiatestoscarc.contactsList.data.RandomUserRepository
import com.example.lydiatestoscarc.contactsList.domain.RandomUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class RandomUserViewModel
@Inject constructor(
    private val randomUserRepository: RandomUserRepository,
): ViewModel() {


    private val _uiState = MutableStateFlow<RandomUserListUiState>(RandomUserListUiState.Loading)
    val uiState: StateFlow<RandomUserListUiState> = _uiState.asStateFlow()

    private val randomUserPage: MutableStateFlow<Int> = MutableStateFlow(1)
    val randomUserList: StateFlow<List<RandomUser>> = randomUserPage.flatMapLatest { page ->
        randomUserRepository.search(
            page = page,
            onStart = { _uiState.tryEmit(RandomUserListUiState.Loading) },
            onComplete = { _uiState.tryEmit(RandomUserListUiState.Idle) },
            onError = { _uiState.tryEmit(RandomUserListUiState.Error(it)) },
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList(),
    )

    fun getNextPage() {
        if (_uiState.value != RandomUserListUiState.Loading) {
            randomUserPage.value++
        }
    }

}

sealed interface RandomUserListUiState {

    data object Idle : RandomUserListUiState

    data object Loading : RandomUserListUiState

    data class Error(val message: String?) : RandomUserListUiState
}