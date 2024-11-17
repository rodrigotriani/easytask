package com.rtriani.easytask.android.ui.feature.login

sealed interface LoginEvent {
    data class UserNameChanged(val username: String) : LoginEvent
    data class PasswordChanged(val password: String) : LoginEvent
    data object Enter : LoginEvent
}