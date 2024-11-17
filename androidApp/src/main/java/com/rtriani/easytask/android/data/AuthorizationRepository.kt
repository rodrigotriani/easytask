package com.rtriani.easytask.android.data

import com.rtriani.easytask.android.domain.Authorization

interface AuthorizationRepository {
    suspend fun InsertAuthorization(entity: AuthorizationEntity)
    suspend fun GetAuthorization(username: String, password: String): Authorization?
}