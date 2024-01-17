package com.example.expenseapproom.screens.homeScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import com.example.expenseapproom.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    val stateItem : LiveData<List<ExpenseDataModel>> = repository.getAllData()

}