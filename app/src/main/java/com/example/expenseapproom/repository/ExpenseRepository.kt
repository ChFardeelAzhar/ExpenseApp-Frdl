package com.example.expenseapproom.repository

import androidx.lifecycle.LiveData
import com.example.expenseapproom.expenseData.ExpenseDao
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import javax.inject.Inject


class ExpenseRepository @Inject constructor(
    private val dao: ExpenseDao
) : ExpenseDao {
    override suspend fun insert(item: ExpenseDataModel) {
        dao.insert(item)
    }

    override fun getAllData(): LiveData<List<ExpenseDataModel>> {
        return dao.getAllData()
    }

    override suspend fun delete(item: ExpenseDataModel) {
        dao.delete(item)
    }

    override suspend fun update(item: ExpenseDataModel) {
        dao.update(item)
    }

}