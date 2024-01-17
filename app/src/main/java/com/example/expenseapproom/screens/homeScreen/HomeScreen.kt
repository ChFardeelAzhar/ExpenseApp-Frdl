@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expenseapproom.screens.homeScreen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Money
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.expenseapproom.expenseData.dataModel.ExpenseDataModel
import com.example.expenseapproom.screens.ListItem
import com.example.expenseapproom.util.NavRouts
import com.example.expenseapproom.viewModel.ExpenseViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ExpenseViewModel = hiltViewModel(),
) {

    val stateItems = viewModel.responseState.value
    val showDeleteDialog = remember {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val context: Context = LocalContext.current
    val selectedItem = remember { mutableStateOf<ExpenseDataModel?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Column {
                    Text(
                        text = "Expense Calculator",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                    Divider(modifier = Modifier.width(189.dp))

                }
            }, modifier = Modifier.fillMaxWidth())
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(NavRouts.addScreen)
                },
                modifier = Modifier.padding(bottom = 20.dp, end = 5.dp),
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ) {
                Image(
                    imageVector = Icons.Outlined.Add,
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.tertiary)
                )
            }
        }
    ) {

        /*
        if (stateItems?.isEmpty() == null ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = Icons.Outlined.Money,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(
                            color = MaterialTheme.colorScheme.tertiary
                        ),
                        modifier = Modifier.alpha(.6f)
                    )
                    Text(
                        text = "No data Found",
                        color = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.alpha(.6f)
                    )
                }
            }
        }
         */


        stateItems?.let { items ->
            if (items.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            imageVector = Icons.Outlined.Money,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.tertiary)
                        )
                        Text(text = "No data Found", color = MaterialTheme.colorScheme.tertiary)
                    }
                }


            } else {
                LazyColumn(modifier = Modifier
                    .padding(it)) {
                    items(items.size) { item ->
                        ListItem(item = items[item], onUpdateClick = {
                            val route = NavRouts.addScreen.replace("{id}",item.toString())
                            navController.navigate(route)

//                            scope.launch {
//                                viewModel.update(items[item])
//                                Toast.makeText(context, "updated Successfully", Toast.LENGTH_SHORT).show()
//                            }

                        }, onDeleteClick = {
                            selectedItem.value = items[item]
                            showDeleteDialog.value = true
                        })
                    }
                }
            }
        }

        /*
        if (stateItems?.isNotEmpty() != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(stateItems) { item ->
                    ListItem(item = item, onUpdateClick = {
                        navController.navigate(NavRouts.addScreen)
                    }, onDeleteClick = {
                        scope.launch {
                            try {
                                viewModel.delete(item)
                                Toast.makeText(
                                    context,
                                    "deleted Successfully... ",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } catch (e: Exception) {
                                if (e is CancellationException) throw e
                                Toast.makeText(context, "${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }
        } else {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        imageVector = Icons.Outlined.Money,
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.tertiary)
                    )
                    Text(text = "No data Found", color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
         */

        if (showDeleteDialog.value) {
            AlertDialog(
                title = {
                    Text(text = "Do you want to delete? ")
                },
                onDismissRequest = {
                    showDeleteDialog.value = false
                },
                confirmButton = {
                    TextButton(onClick = {
                        scope.launch {
                            viewModel.delete(selectedItem.value!!)
                            showDeleteDialog.value = false
                        }
                    }) {
                        Text(text = "Yes")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDeleteDialog.value = false
                    }) {
                        Text(text = "No")
                    }
                }
            )
        }
    }
}
