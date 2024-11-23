package com.rtriani.easytask.android.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rtriani.easytask.android.domain.StatusEnum
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDAL {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun Insert(entity: TodoEntity)

    @Delete
    suspend fun Delete(entity: TodoEntity)

    @Query("SELECT * FROM todos WHERE status = :status")
    fun GetAll(status: StatusEnum): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE title LIKE '%' || :title || '%' AND status = :status")
    fun Filter(title: String, status: StatusEnum): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todos WHERE id = :id")
    suspend fun GetById(id: Long): TodoEntity?
}