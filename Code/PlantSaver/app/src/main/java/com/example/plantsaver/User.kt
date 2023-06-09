package com.example.plantsaver

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "users")
data class User(
    @PrimaryKey val id: Long,
    val username: String

)
