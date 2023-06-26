package com.example.plantsaver.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant")
data class Plant(
    val name: String,
    val plantFamilyId: Long, // foreign key
    val userName: String, // foreign key
    val mon: Boolean = false,
    val tue: Boolean = false,
    val wed: Boolean = false,
    val thu: Boolean = false,
    val fri: Boolean = false,
    val sat: Boolean = false,
    val sun: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
){

}