package com.yoo.roomcomposemvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yoo.roomcomposemvvm.database.UserDatabase
import com.yoo.roomcomposemvvm.detail.DetailScreen
import com.yoo.roomcomposemvvm.home.HomeScreen
import com.yoo.roomcomposemvvm.repository.UserRepository
import com.yoo.roomcomposemvvm.ui.theme.RoomComposeMVVMTheme
import com.yoo.roomcomposemvvm.viewModel.MainViewModel
import com.yoo.roomcomposemvvm.viewModel.MainViewModelFactory
import com.yoo.roomcomposemvvm.viewModel.UserEvent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val repository =UserRepository(UserDatabase(this).dao)
        val viewModel:MainViewModel by viewModels {
            MainViewModelFactory(repository)
        }
        super.onCreate(savedInstanceState)
        setContent {
            RoomComposeMVVMTheme {
                val navHostController = rememberNavController()
                NavHost(
                    navController = navHostController,
                    startDestination = "home_screen"
                ) {
                    composable(route = "home_screen") {
                        val userList by viewModel.userList.observeAsState()
                        HomeScreen(
                            userList= userList ?: emptyList(),
                            onItemClick = {
                                navHostController.navigate("detail_screen/$it")
                            },
                            onNavigate = {
                                navHostController.navigate("detail_screen/0")
                            },
                            onDelete = {user ->
                                viewModel.onEvent(UserEvent.OnDeleteUser(user))
                            }
                        )
                    }

                    composable(
                        route = "detail_screen/{id}",
                        arguments = listOf(
                            navArgument(
                                name = "id"
                            ) {
                                type = NavType.IntType
                            }
                        )
                    ) {
                        val id = it.arguments?.getInt("id") ?: 0
                        DetailScreen(
                            id = id,
                            onBack = { navHostController.popBackStack() },
                            viewModel=viewModel
                        )
                    }

                }
            }
        }
    }
}