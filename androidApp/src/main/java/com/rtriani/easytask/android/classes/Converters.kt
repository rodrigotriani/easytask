package com.rtriani.easytask.android.classes

import androidx.room.TypeConverter
import com.rtriani.easytask.android.domain.StatusEnum
import java.util.Date

class Converters {
    @TypeConverter
    fun statusFromString(value: String?): StatusEnum {
        return StatusEnum.valueOf(value ?: "PENDENTE")
    }

    @TypeConverter
    fun statusToString(status: StatusEnum?): String {
        return status?.name ?: "PENDENTE"
    }
}