package com.yoo.roomcomposemvvm.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yoo.roomcomposemvvm.database.User
import com.yoo.roomcomposemvvm.ui.theme.Lottie

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userList: List<User>,
    onItemClick: (Long) ->Unit,
    onDelete:(User) -> Unit,
    onNavigate: () ->Unit
) {

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title ={
                    Text(text = "USER LIST", style = LocalTextStyle.current, color = Color.White)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(MaterialTheme.colorScheme.primary)

            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigate ) {
                Icon(imageVector = Icons.Outlined.Add, contentDescription = "add")
            }
        }

    ) {padding ->
        if (userList.isEmpty()){
            Lottie()
        }
        
        LazyColumn(
            modifier = Modifier
                .padding(padding),
                contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            itemsIndexed(
                items = userList,
                key = {i, u -> u.id}
            ){i, u ->
                UserItem(
                    user = u,
                    index = i,
                    onItemClick = onItemClick,
                    onDelete = onDelete
                )
            }
        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserItem(
    user : User,
    index:Int,
    onItemClick: (Long) -> Unit,
    onDelete: (User) -> Unit
) {
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        onClick = {
            onItemClick(user.id)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = index.plus(1).toString(),
                style = MaterialTheme.typography.titleLarge
            )

            Column(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = user.fulName,
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = user.age.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            IconButton(onClick = { onDelete(user) }) {

                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "delete"
                )
                
            }
            
        }

    }
    
}
