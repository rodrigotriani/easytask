package com.rtriani.easytask.android.ui.feature.addedit

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtriani.easytask.android.classes.convertStringToDate
import com.rtriani.easytask.android.data.TodoRepository
import com.rtriani.easytask.android.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.Date

class AddEditViewModel(
    private val id: Long? = null,
    private val repository: TodoRepository
) : ViewModel() {
    var title: String by mutableStateOf(value = "")
        private set

    var description: String? by mutableStateOf(value = null)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        id?.let {
            viewModelScope.launch {
                repository.GetById(it)?.let { todo ->
                    title = todo.title
                    description = todo.description ?: ""
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

            repository.Insert(title, description, id)
            _uiEvent.send(UIEvent.ShowSnackbar("Tarefa salva com sucesso"))
            _uiEvent.send(UIEvent.NavigateBack)
        }
    }
}