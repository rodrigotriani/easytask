package com.rtriani.easytask.android.ui.feature.list

import com.rtriani.easytask.android.domain.StatusEnum

sealed interface ListEvent {
    data class Delete(val id: Long) : ListEvent
    data class AddEdit(val id: Long?) : ListEvent
    data class Profile(val id: Long) : ListEvent
    data class SearchChanged(val search: String, val status: StatusEnum = StatusEnum.PENDENTE) : ListEvent
}