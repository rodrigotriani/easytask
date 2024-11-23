package com.rtriani.easytask.android.data

import com.rtriani.easytask.android.domain.StatusEnum
import com.rtriani.easytask.android.domain.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    suspend fun Insert(title: String, description: String?, status: StatusEnum, id:Long? = null)
    suspend fun Delete(id: Long)
    fun GetAll(status: StatusEnum): Flow<List<Todo>>
    fun Filter(title: String, status: StatusEnum): Flow<List<Todo>>
    suspend fun GetById(id: Long): Todo?
}