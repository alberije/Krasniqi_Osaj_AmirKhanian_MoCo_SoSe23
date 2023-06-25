package com.example.plantsaver.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsaver.database.model.User


@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: Long): User?

}