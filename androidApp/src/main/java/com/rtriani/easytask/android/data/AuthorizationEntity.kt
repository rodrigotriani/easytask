package com.rtriani.easytask.android.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "authorization")
data class AuthorizationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val username: String,
    val password: String
)