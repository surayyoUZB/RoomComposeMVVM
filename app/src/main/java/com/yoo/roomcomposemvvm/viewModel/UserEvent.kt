package com.yoo.roomcomposemvvm.viewModel

import com.yoo.roomcomposemvvm.database.User

sealed interface UserEvent {
//    object OnGetAllUsers : UserEvent
    data class OnSaveUser(val user:User):UserEvent
    data class OnDeleteUser(val user:User):UserEvent
    data class OnUpdateUser(val user:User):UserEvent
    data class OnGEtUserById(val id:Int):UserEvent

}