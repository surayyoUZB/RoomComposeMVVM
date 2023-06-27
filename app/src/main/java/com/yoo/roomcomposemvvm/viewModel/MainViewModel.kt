package com.yoo.roomcomposemvvm.viewModel


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoo.roomcomposemvvm.database.User
import com.yoo.roomcomposemvvm.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: UserRepository
):ViewModel() {
    var userList = repository.getAllUsers()
    val user=MutableLiveData<User?>(null)

    fun getUserById(id:Int):LiveData<User?>{
        return repository.getUserById(id)
    }

    fun onEvent(event: UserEvent){
        when(event){
            is UserEvent.OnGEtUserById -> {
                viewModelScope.launch {
                    val queryUser =repository.getUserById(event.id)
                    user.postValue(queryUser.value)
                }
            }
            is UserEvent.OnSaveUser -> {
                viewModelScope.launch(Dispatchers.IO){
                    repository.saveUser(event.user)
                }
            }
            is UserEvent.OnDeleteUser -> {
                viewModelScope.launch(Dispatchers.IO){
                    repository.deleteUser(event.user)
                }
            }
            is UserEvent.OnUpdateUser -> {
                viewModelScope.launch(Dispatchers.IO){
                    repository.updateUser(event.user)
                }
            }
            is UserEvent.OnGEtUserById -> {
                viewModelScope.launch(Dispatchers.IO){
                    repository.getUserById(event.id)
                }
            }
        }
    }

}