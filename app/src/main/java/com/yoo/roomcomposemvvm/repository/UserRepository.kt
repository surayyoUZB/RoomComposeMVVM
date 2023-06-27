package com.yoo.roomcomposemvvm.repository

import com.yoo.roomcomposemvvm.database.User
import com.yoo.roomcomposemvvm.database.UserDao

class UserRepository(
    private  val dao: UserDao
) {
    suspend fun saveUser(user:User) = dao.saveUser(user)
    suspend fun deleteUser(user: User) = dao.deleteUser(user)
    suspend fun updateUser(user: User) = dao.updateUser(user)
    fun getUserById(id:Int) = dao.getUserById(id)
    fun getAllUsers() = dao.getAllUsers()
}