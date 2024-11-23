package com.rtriani.easytask.android.ui.feature.addedit

import com.rtriani.easytask.android.domain.StatusEnum

sealed interface AddEditEvent {
    data class TitleChanged(val title: String) : AddEditEvent
    data class DescriptionChanged(val description: String) : AddEditEvent
    data class StatusChanged(val status: StatusEnum) : AddEditEvent
    data object Save : AddEditEvent
}