package com.example.plantsaver.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")

data class User(

    @PrimaryKey

    val username: String

)
