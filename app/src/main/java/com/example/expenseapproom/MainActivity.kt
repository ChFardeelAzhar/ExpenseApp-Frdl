@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.expenseapproom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.expenseapproom.screens.addScreen.Add_Screen
import com.example.expenseapproom.screens.homeScreen.HomeScreen
import com.example.expenseapproom.ui.theme.ExpenseAppRoomTheme
import com.example.expenseapproom.util.NavRouts
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpenseAppRoomTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavRouts.homeScreen) {
        composable(NavRouts.homeScreen) {
            HomeScreen(navController = navController)
        }
        composable(NavRouts.addScreen) {
            Add_Screen(navController)
        }
    }

}

