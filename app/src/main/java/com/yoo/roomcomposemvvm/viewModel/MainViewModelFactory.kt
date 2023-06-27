package com.yoo.roomcomposemvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoo.roomcomposemvvm.repository.UserRepository

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: UserRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }

}