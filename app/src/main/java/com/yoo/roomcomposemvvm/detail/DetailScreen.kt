package com.yoo.roomcomposemvvm.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yoo.roomcomposemvvm.MainActivity
import com.yoo.roomcomposemvvm.database.User
import com.yoo.roomcomposemvvm.viewModel.MainViewModel
import com.yoo.roomcomposemvvm.viewModel.UserEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailScreen(
    id:Int,
    onBack:()->Unit,
    viewModel: MainViewModel
) {

   var fulName by remember { mutableStateOf("") }
   var age by remember { mutableStateOf("") }
    val snackBar = remember{SnackbarHostState()}
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<User?>(null) }
    val keyBoardController = LocalSoftwareKeyboardController.current
    val context= LocalContext.current as MainActivity

    if (id!=0){
        LaunchedEffect(key1 = Unit){
            viewModel.getUserById(id)
                .observe(context){
                    user = it
                }
        }
    }
    user?.let {
        fulName=it.fulName
        age=it.age.toString()
    }



    Scaffold(
        snackbarHost = {
                       SnackbarHost(hostState = snackBar)
        },
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = if (id==0)  "Add New User" else "Update User", color = Color.White )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary),
                navigationIcon = {
                    IconButton(onClick =  onBack) {
                        Icon(imageVector = Icons.Outlined.ArrowBack, contentDescription = "back", tint = Color.White)
                    }
                },
                actions = {
                    keyBoardController!!.hide()
                    IconButton(
                        onClick = {
                            if (fulName.isNotBlank() && age.isNotBlank()){
                                if (id==0){
                                    viewModel.onEvent(UserEvent.OnSaveUser(User(fulName, age.toInt())))
                                    scope.launch {
                                        snackBar.showSnackbar("User saved successfully")
                                    }
                                }else {
                                    viewModel.onEvent(UserEvent.OnUpdateUser(User(fulName, age.toInt(), id.toLong())))
                                    scope.launch {
                                        snackBar.showSnackbar("User updated successfully")
                                    }
                                }
                            }else{
                                scope.launch {
                                    snackBar.showSnackbar("Enter data correctly")
                                }
                            }
                        }) {
                        Icon(imageVector = Icons.Outlined.Done, contentDescription = "back",  tint = Color.White)

                    }
                }
            )
        }

    ) {paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = fulName,
                onValueChange = {fulName=it},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                label = {
                        Text(text = "Full name")
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(20.dp))


            OutlinedTextField(
                value = age,
                onValueChange = {age=it},
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                label = {
                    Text(text = "Age")
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))




        }


    }

}