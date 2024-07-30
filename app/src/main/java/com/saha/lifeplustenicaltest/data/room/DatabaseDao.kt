package com.saha.lifeplustenicaltest.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saha.lifeplustenicaltest.data.model.User

@Dao
interface DatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveUser(user: User)

    @Query("SELECT * FROM users WHERE users.userName == :userName")
    fun getUser(userName: String): User

    @Delete
    fun deleteUser(user: User)

}