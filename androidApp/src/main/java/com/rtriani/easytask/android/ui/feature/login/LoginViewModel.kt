package com.rtriani.easytask.android.ui.feature.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rtriani.easytask.android.data.AuthorizationRepository
import com.rtriani.easytask.android.navigation.ListRoute
import com.rtriani.easytask.android.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val repository: AuthorizationRepository
): ViewModel() {
    var username by mutableStateOf(value = "")
        private set

    var password by mutableStateOf(value = "")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: LoginEvent){
        when(event){
            is LoginEvent.UserNameChanged -> {
                username = event.username
            }
            is LoginEvent.PasswordChanged -> {
                password = event.password
            }
            LoginEvent.Enter -> {
                Enter()
            }
        }
    }

    private fun Enter(){
        viewModelScope.launch {
            if(username.isBlank()){
                _uiEvent.send(UIEvent.ShowSnackbar("O usuário não pode estar em branco"))
                return@launch
            }

            if(password.isBlank()){
                _uiEvent.send(UIEvent.ShowSnackbar("a senha não pode estar em branco"))
                return@launch
            }

            val authorization = repository.GetAuthorization(username, password)
            if(authorization == null){
                _uiEvent.send(UIEvent.ShowSnackbar("Usuário ou senha inválidos"))
                return@launch
            }
            _uiEvent.send(UIEvent.Navigate(ListRoute))
        }
    }
}