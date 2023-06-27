package com.yoo.roomcomposemvvm.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    val fulName:String,
    val age:Int,
    @PrimaryKey(autoGenerate = true)
    var id:Long=0
){
}
