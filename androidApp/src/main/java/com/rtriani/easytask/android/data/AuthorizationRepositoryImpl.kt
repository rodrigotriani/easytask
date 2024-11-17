package com.rtriani.easytask.android.data

import com.rtriani.easytask.android.domain.Authorization

class AuthorizationRepositoryImpl(
    private val dal: AuthorizationDAL
): AuthorizationRepository {
    override suspend fun InsertAuthorization(authorizationEntity: AuthorizationEntity){
        dal.InsertAuthorization(authorizationEntity)
    }

    override suspend fun GetAuthorization(username: String, password: String): Authorization?{
        dal.InsertAuthorization(AuthorizationEntity(
            id = 1,
            username = "ibm",
            password = "ibm"
        ))
        return dal.GetAuthorization(username, password)?.let{ entity ->
            Authorization(
                id = entity.id,
                username = entity.username,
                password = entity.password
            )
        };
    }
}