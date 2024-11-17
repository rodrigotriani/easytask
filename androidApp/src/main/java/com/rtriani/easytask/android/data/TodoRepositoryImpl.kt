package com.rtriani.easytask.android.data

import com.rtriani.easytask.android.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dal: TodoDAL
): TodoRepository {
    override suspend fun Insert(title: String, description: String?, id: Long?) {
        val todo = id?.let {
            dal.GetById(id)?.copy(
                title = title,
                description = description
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            isCompleted = false
        )
        dal.Insert(todo)
    }

    override suspend fun UpdateCompleted(id: Long, isCompleted: Boolean) {
        val todo = dal.GetById(id)
        todo?.let {
            dal.Insert(it.copy(isCompleted = isCompleted))
        }
    }

    override suspend fun Delete(id: Long) {
        val todo = dal.GetById(id)?:return
        dal.Delete(todo)
    }

    override fun GetAll(): Flow<List<Todo>> {
        return dal.GetAll().map { entities->
            entities.map{entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }

    override fun GetByTitle(title: String): Flow<List<Todo>> {
        return dal.GetByTitle(title).map { entities->
            entities.map{entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    isCompleted = entity.isCompleted
                )
            }
        }
    }

    override suspend fun GetById(id: Long): Todo? {
        return dal.GetById(id)?.let { entity ->
            Todo(
                id = entity.id,
                title = entity.title,
                description = entity.description,
                isCompleted = entity.isCompleted
            )
        }
    }
}