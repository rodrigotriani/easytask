package com.rtriani.easytask.android.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorizationDAL {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun InsertAuthorization(entity: AuthorizationEntity)

    @Query("SELECT * FROM authorization WHERE username = :username AND password = :password")
    suspend fun GetAuthorization(username: String, password: String): AuthorizationEntity?
}