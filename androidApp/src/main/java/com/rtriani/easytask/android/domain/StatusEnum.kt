package com.rtriani.easytask.android.domain

enum class StatusEnum(val status: String) {
    PENDENTE (status = "Pendente"),
    EM_ANDAMENTO (status = "Em Andamento"),
    FINALIZADO (status = "Finalizado")
}