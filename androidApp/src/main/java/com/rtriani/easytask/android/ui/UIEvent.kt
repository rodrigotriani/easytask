package com.rtriani.easytask.android.ui

sealed interface UIEvent {
    data class ShowSnackbar(val message: String) : UIEvent
    data object NavigateBack: UIEvent
    data class Navigate<T>(val route:T): UIEvent
}