package com.example.expenseapproom.screens.addScreen

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import com.example.expenseapproom.repository.ExpenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AddScreenViewModel @Inject constructor(
    private val repository: ExpenseRepository,
) : ViewModel() {


    init {

    }

    fun currentStoredData(
        id: Long? = null,
        typeOfData: String,
        date: Long = System.currentTimeMillis(),
        amount: Double,
        description: String
    ) {

        CoroutineScope(Dispatchers.IO).launch {
            val data = ExpenseDataModel(
                id = id,
                typeOfData = typeOfData,
                date = date,
                amount = amount,
                description = description
            )

            try {
                if (id != null) {
                    repository.insert(data)

                } else {
                    repository.update(data)
                }
            } catch (e: Exception) {
                if (e is CancellationException) throw e
            }

        }
    }


}