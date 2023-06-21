package com.example.plantsaver.ui.theme.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantsaver.ui.theme.database.Plant
import com.example.plantsaver.ui.theme.database.PlantDao
import com.example.plantsaver.ui.theme.database.User
import com.example.plantsaver.ui.theme.database.UserDao

@Database(entities = [User::class,
                      Plant::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao

}

