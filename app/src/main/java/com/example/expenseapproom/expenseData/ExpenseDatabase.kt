package com.example.expenseapproom.expenseData

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel

@Database(entities = [ExpenseDataModel::class], version = 1)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun getDao(): ExpenseDao
}