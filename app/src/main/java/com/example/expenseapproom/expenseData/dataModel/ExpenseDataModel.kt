package com.example.expenseapproom.expenseData.dataModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class ExpenseDataModel(

    @PrimaryKey(autoGenerate = true) val id: Long?,
    val typeOfData: String,
    val date: Long,
    val amount: Double,
    val description: String

)