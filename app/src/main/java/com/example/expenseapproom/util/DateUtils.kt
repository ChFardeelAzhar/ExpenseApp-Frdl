package com.example.expenseapproom.util

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatData(date: Long): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}