package com.example.plantsaver.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantsaver.Plant
import com.example.plantsaver.database.model.User

@Database(entities = [User::class,
                      Plant::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun plantDao(): PlantDao

}

