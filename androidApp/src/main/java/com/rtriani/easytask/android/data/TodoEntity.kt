package com.rtriani.easytask.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rtriani.easytask.android.domain.StatusEnum

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String?,
    val status: StatusEnum = StatusEnum.PENDENTE
)
