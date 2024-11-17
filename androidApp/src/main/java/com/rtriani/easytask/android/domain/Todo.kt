package com.rtriani.easytask.android.domain

import android.annotation.SuppressLint
import java.util.Date

data class Todo(
    val id: Long,
    val title: String,
    val description: String?,
    val isCompleted: Boolean
)

//Fake Objects

@SuppressLint("NewApi")
val todo1 = Todo(1, "Todo 1", "Description 1", false)
@SuppressLint("NewApi")
val todo2 = Todo(2, "Todo 2", "Description 2", true)
@SuppressLint("NewApi")
val todo3 = Todo(3, "Todo 3", "Description 3", false)