package com.rtriani.easytask.android.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAL {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(entity: TodoEntity)

    @Delete
    suspend fun Delete(entity: TodoEntity)

    @Query("SELECT * FROM todos")
    fun GetAll(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE title LIKE '%' || :title || '%'")
    fun GetByTitle(title: String): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun GetById(id: Long): TodoEntity?
}