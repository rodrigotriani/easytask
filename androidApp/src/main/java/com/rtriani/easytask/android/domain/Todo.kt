package com.rtriani.easytask.android.domain

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val status: StatusEnum = StatusEnum.PENDENTE
)

val todo1 = Todo(1, "Todo 1", "Description 1", StatusEnum.PENDENTE)
val todo2 = Todo(2, "Todo 2", "Description 2", StatusEnum.EM_ANDAMENTO)