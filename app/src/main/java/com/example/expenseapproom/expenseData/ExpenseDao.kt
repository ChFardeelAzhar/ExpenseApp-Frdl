package com.example.expenseapproom.expenseData

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel

@Dao
interface ExpenseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ExpenseDataModel)

    @Query("SELECT * FROM ExpenseDataModel")
    fun getAllData(): LiveData<List<ExpenseDataModel>>

    @Delete
    suspend fun delete(item: ExpenseDataModel)

    @Update
    suspend fun update(item: ExpenseDataModel)

}