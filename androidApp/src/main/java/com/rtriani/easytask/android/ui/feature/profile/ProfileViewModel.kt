package com.rtriani.easytask.android.ui.feature.profile

import androidx.lifecycle.ViewModel
import com.rtriani.easytask.android.data.AuthorizationRepository
import com.rtriani.easytask.android.ui.UIEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class ProfileViewModel(
    private val repository: AuthorizationRepository
): ViewModel() {
    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
}