package com.rtriani.easytask.android.ui.feature.list

sealed interface ListEvent {
    data class Delete(val id: Long) : ListEvent
    data class CompleteChanged(val id: Long, val isComplete: Boolean) : ListEvent
    data class AddEdit(val id: Long?) : ListEvent
    data class Profile(val id: Long) : ListEvent
    data class SearchChanged(val search: String) : ListEvent
}