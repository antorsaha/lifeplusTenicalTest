package com.saha.lifeplustenicaltest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(

    val name: String,
    @PrimaryKey
    val userName: String,
    val password: String,
    val phoneNumber: String
)
