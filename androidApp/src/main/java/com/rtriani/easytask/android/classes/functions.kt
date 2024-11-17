package com.rtriani.easytask.android.classes

import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

fun GetDateFormatted(dataTarefa: Date, pattern: String):String{
    val sdf: SimpleDateFormat
    sdf = SimpleDateFormat(pattern)
    sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
    val dateText: String = sdf.format(dataTarefa)
    return dateText
}

fun convertStringToDate(string: String): Date{
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.parse(string)
}