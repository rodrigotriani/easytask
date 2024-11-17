package com.rtriani.easytask.android.domain

data class Authorization(
    val id: Long,
    val username: String,
    val password: String
)

val authorization1 = Authorization(1,"ibm", "ibm");