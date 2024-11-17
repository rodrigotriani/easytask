package com.rtriani.easytask.android.ui.feature.list

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtriani.easytask.android.data.TodoRepository
import com.rtriani.easytask.android.navigation.AddEditRoute
import com.rtriani.easytask.android.navigation.ProfileRoute
import com.rtriani.easytask.android.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ListViewModel(
    private val repository: TodoRepository
): ViewModel() {
    var todos = repository.GetAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    var search: String? by mutableStateOf(value = null)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: ListEvent) {
        when(event) {
            is ListEvent.Delete -> {
                delete(id = event.id)
            }
            is ListEvent.CompleteChanged -> {
                completeChanged(id = event.id, isComplete = event.isComplete)
            }
            is ListEvent.AddEdit -> {
                viewModelScope.launch {
                    _uiEvent.send(UIEvent.Navigate(AddEditRoute(event.id)))
                }
            }
            is ListEvent.Profile -> {
                viewModelScope.launch {
                    _uiEvent.send(UIEvent.Navigate(ProfileRoute))
                }

            }
            is ListEvent.SearchChanged -> {
                search = event.search
                if(event.search.isBlank()){
                    todos = repository.GetAll().stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = emptyList()
                    )
                }else{
                    todos = repository.GetByTitle(event.search).stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5000),
                        initialValue = emptyList()
                    )
                }
            }

        }
    }

    private fun delete(id: Long){
        viewModelScope.launch {
            repository.Delete(id)
        }
    }

    private fun completeChanged(id: Long, isComplete: Boolean) {
        viewModelScope.launch {
            repository.UpdateCompleted(id, isComplete)
        }

    }
}