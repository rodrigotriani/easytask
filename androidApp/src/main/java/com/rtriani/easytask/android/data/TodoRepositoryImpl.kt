package com.rtriani.easytask.android.data

import com.rtriani.easytask.android.domain.StatusEnum
import com.rtriani.easytask.android.domain.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TodoRepositoryImpl(
    private val dal: TodoDAL
): TodoRepository {
    override suspend fun Insert(title: String, description: String?, status: StatusEnum, id: Long?) {
        val todo = id?.let {
            dal.GetById(id)?.copy(
                title = title,
                description = description,
                status = status
            )
        } ?: TodoEntity(
            title = title,
            description = description,
            status = status
        )
        dal.Insert(todo)
    }

    override suspend fun Delete(id: Long) {
        val todo = dal.GetById(id)?:return
        dal.Delete(todo)
    }

    override fun GetAll(status: StatusEnum): Flow<List<Todo>> {
        return dal.GetAll(status).map { entities->
            entities.map{entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    status = entity.status
                )
            }
        }
    }

    override fun Filter(title: String, status: StatusEnum): Flow<List<Todo>> {
        return dal.Filter(title, status).map { entities->
            entities.map{entity ->
                Todo(
                    id = entity.id,
                    title = entity.title,
                    description = entity.description,
                    status = entity.status
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
                status = entity.status
            )
        }
    }
}