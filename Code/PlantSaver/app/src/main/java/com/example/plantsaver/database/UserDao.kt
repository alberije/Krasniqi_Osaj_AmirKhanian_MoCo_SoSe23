package com.example.plantsaver.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.plantsaver.database.model.User


@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE username LIKE :name")
    suspend fun getUserByName(name: String): User?

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<User>

}