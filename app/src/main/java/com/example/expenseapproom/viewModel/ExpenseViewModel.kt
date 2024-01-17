package com.example.expenseapproom.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenseapproom.repository.ExpenseRepository
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val repository: ExpenseRepository
) : ViewModel() {

    val response = mutableStateOf(repository.getAllData())
    val responseState: MutableState<List<ExpenseDataModel>> = mutableStateOf(listOf())

    init {
        response.value?.observeForever { data ->
            data?.let {
                responseState.value = data
            }
        }
    }

    fun insert(item: ExpenseDataModel) {
        viewModelScope.launch {
            repository.insert(item)
        }
    }

    fun delete(item: ExpenseDataModel) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun update(item: ExpenseDataModel) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

}