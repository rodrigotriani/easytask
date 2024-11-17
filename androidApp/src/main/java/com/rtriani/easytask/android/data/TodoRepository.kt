package com.rtriani.easytask.android.data

import com.rtriani.easytask.android.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun Insert(title: String, description: String?, id:Long? = null)
    suspend fun UpdateCompleted(id: Long, isCompleted: Boolean)
    suspend fun Delete(id: Long)
    fun GetAll(): Flow<List<Todo>>
    fun GetByTitle(title: String): Flow<List<Todo>>
    suspend fun GetById(id: Long): Todo?
}