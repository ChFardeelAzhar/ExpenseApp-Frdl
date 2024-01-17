package com.example.expenseapproom.screens.addScreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import com.example.expenseapproom.util.DateUtils
import com.example.expenseapproom.util.NavRouts
import com.example.expenseapproom.util.TypeOfData
import com.example.expenseapproom.viewModel.ExpenseViewModel
import kotlinx.coroutines.launch


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Add_Screen(
    navController: NavController,
    viewModel: AddScreenViewModel = hiltViewModel(),
    dataModel: ExpenseDataModel? = null
) {

    var amount by remember {
        mutableStateOf(
            TextFieldValue(
                text = dataModel?.amount?.toString() ?: ""
            )
        )
    }

    var description by remember {
        mutableStateOf(
            TextFieldValue(
                text = dataModel?.description ?: ""
            )
        )
    }
    var selected by remember {
        mutableStateOf(
            if (dataModel?.typeOfData == TypeOfData.TypeOfData.Income.value) {
                TypeOfData.TypeOfData.Income
            } else TypeOfData.TypeOfData.Expense
        )
    }

    val selectedButtonColor = MaterialTheme.colorScheme.tertiaryContainer
    val unselectedButtonColor = MaterialTheme.colorScheme.onBackground

    val datePickerState =
        rememberDatePickerState(if (dataModel?.date != null) dataModel.date else System.currentTimeMillis())

    var selectedDate by remember {
        mutableStateOf(
            TextFieldValue(
                text = DateUtils.formatData(
                    datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                )
            )
        )
    }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context: Context = LocalContext.current



    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        selectedDate = TextFieldValue(
            text = DateUtils.formatData(
                datePickerState.selectedDateMillis ?: System.currentTimeMillis()
            )
        )
    }

    Scaffold(
        topBar = {
            Box(
                modifier = Modifier.padding(5.dp),
                contentAlignment = Alignment.TopStart
            ) {
                Image(
                    imageVector = Icons.Outlined.ArrowBack,
                    contentDescription = null,
                    modifier = Modifier
                        .padding(7.dp)
                        .clickable {
                            navController.popBackStack()
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .clip(shape = RoundedCornerShape(7.dp))
                    .background(color = MaterialTheme.colorScheme.onBackground),
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Button(
                    onClick = {
                        selected = TypeOfData.TypeOfData.Expense
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 3.dp),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        if (selected == TypeOfData.TypeOfData.Expense) selectedButtonColor else unselectedButtonColor
                    ),
                ) {
                    Text(
                        text = TypeOfData.TypeOfData.Expense.value,
                        fontWeight = if (selected == TypeOfData.TypeOfData.Expense) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected == TypeOfData.TypeOfData.Expense) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background
                    )
                }


                Button(
                    onClick = {
                        selected = TypeOfData.TypeOfData.Income
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 3.dp),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor =
                        if (selected == TypeOfData.TypeOfData.Income) selectedButtonColor else unselectedButtonColor
                    ),
                ) {
                    Text(
                        text = TypeOfData.TypeOfData.Income.value,
                        fontWeight = if (selected == TypeOfData.TypeOfData.Income) FontWeight.Bold else FontWeight.Normal,
                        color = if (selected == TypeOfData.TypeOfData.Income) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.background

                    )
                }
            }

            Spacer(modifier = Modifier.height(13.dp))

            OutlinedTextField(
                value = selectedDate.text,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .focusable(true)
                    .clickable {
                        showDialog = true
                    },
                readOnly = true,
                enabled = false,
                leadingIcon = {
                    Image(
                        imageVector = Icons.Outlined.DateRange,
                        contentDescription = "Set Date",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error)
                    )
                },
                label = { Text(text = "Select Date") },
                colors = TextFieldDefaults.colors(
                    disabledIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    disabledTextColor = MaterialTheme.colorScheme.onBackground,
                    disabledLabelColor = MaterialTheme.colorScheme.onBackground,
                    disabledContainerColor = MaterialTheme.colorScheme.background
                )
            )



            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Image(
                        imageVector = Icons.Outlined.Money,
                        contentDescription = "Income/Expense_Amount",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error)
                    )
                },
                label = { Text(text = "Enter Amount") },

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    Image(
                        imageVector = Icons.Outlined.Description,
                        contentDescription = "Income/Expense_Amount",
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.error)
                    )
                },
                label = { Text(text = "Add Description") },
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomCenter
            ) {

                Button(
                    onClick = {

                        /*
                             if (dataModel?.id != null) {
                                 scope.launch {
                                     viewModel.update(
                                         item = ExpenseDataModel(
                                             id = dataModel.id,
                                             typeOfData = selected.value,
                                             date = datePickerState.selectedDateMillis
                                                 ?: System.currentTimeMillis(),
                                             amount = amount.text.toDouble(),
                                             description = description.text,
                                         )
                                     )
                                     navController.navigate(NavRouts.homeScreen)
                                     Toast.makeText(
                                         context,
                                         "Updated Successfully...",
                                         Toast.LENGTH_SHORT
                                     ).show()
                                 }
                             } else {
                                 scope.launch {
                                     viewModel.insert(
                                         item = ExpenseDataModel(
                                             id = dataModel?.id,
                                             typeOfData = selected.value,
                                             date = datePickerState.selectedDateMillis
                                                 ?: System.currentTimeMillis(),
                                             amount = amount.text.toDouble(),
                                             description = description.text,
                                         )
                                     )
                                     navController.navigate(NavRouts.homeScreen)
                                     Toast.makeText(
                                         context,
                                         "Item added successfully...",
                                         Toast.LENGTH_SHORT
                                     ).show()

                                 }
                             }
                         */
                        viewModel.currentStoredData(
                            id = dataModel?.id,
                            typeOfData = selected.value,
                            date = datePickerState.selectedDateMillis
                                ?: System.currentTimeMillis(),
                            amount = amount.text.toDouble(),
                            description = description.text,
                        )
                        navController.navigate(NavRouts.addScreen)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                        .border(
                            1.dp,
                            shape = RoundedCornerShape(7.dp),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        .height(45.dp),
                    shape = RoundedCornerShape(7.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = selectedButtonColor),
                ) {
                    Text(
                        text = "Save",
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.titleMedium,

                        )
                }
            }

        }


        if (showDialog) {
            DatePickerDialog(
                onDismissRequest = { showDialog = false },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "Ok")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

    }

}
