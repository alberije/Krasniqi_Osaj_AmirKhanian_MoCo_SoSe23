package com.example.plantsaver

import androidx.room.Database

@Database(entities = [User::class, Plant::class], version = 1)
abstract class AppDatabase {
    abstract fun userDao():  UserDao
    abstract fun plantDao(): PlantDao
}

