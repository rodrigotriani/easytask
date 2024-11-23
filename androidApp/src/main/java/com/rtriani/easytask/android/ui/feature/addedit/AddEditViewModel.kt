package com.rtriani.easytask.android.ui.feature.addedit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtriani.easytask.android.data.TodoRepository
import com.rtriani.easytask.android.domain.StatusEnum
import com.rtriani.easytask.android.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AddEditViewModel(
    private val id: Long? = null,
    private val repository: TodoRepository
) : ViewModel() {
    var title: String by mutableStateOf(value = "")
        private set

    var description: String? by mutableStateOf(value = null)
        private set

    var status: StatusEnum by mutableStateOf(value = StatusEnum.PENDENTE)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
            viewModelScope.launch {
                repository.GetById(it)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
                    status = todo.status
                }
            }
        }
    }

    fun onEvent(event: AddEditEvent){
        when(event){
            is AddEditEvent.TitleChanged -> {
                title = event.title
            }
            is AddEditEvent.DescriptionChanged -> {
                description = event.description
            }
            is AddEditEvent.StatusChanged -> {
                status = event.status
            }
            AddEditEvent.Save -> {
                Save()
            }
        }
    }

    private fun Save(){
        viewModelScope.launch {
            if(title.isBlank()){
                _uiEvent.send(UIEvent.ShowSnackbar("O título não pode estar em branco"))
                return@launch
            }

            repository.Insert(title, description, status, id)
            _uiEvent.send(UIEvent.ShowSnackbar("Tarefa salva com sucesso"))
            _uiEvent.send(UIEvent.NavigateBack)
        }
    }
}