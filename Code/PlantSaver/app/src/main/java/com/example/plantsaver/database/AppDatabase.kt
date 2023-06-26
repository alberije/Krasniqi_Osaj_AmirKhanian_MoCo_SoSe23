package com.example.plantsaver.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.plantsaver.database.model.Plant
import com.example.plantsaver.database.model.PlantFamily
import com.example.plantsaver.database.model.User

@Database(entities = [User::class,
                      Plant::class, PlantFamily::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract val userDao: UserDao
    abstract val plantDao: PlantDao
    abstract val plantFamilyDao: PlantFamilyDao


    // damit es nur eine instanz von der datenbank gibt, sonst macht sqlite probleme
    companion object {
        private lateinit var dbInstance: AppDatabase
        fun getDatabase(context: Context): AppDatabase {
            synchronized(AppDatabase::class.java) {
                if (!::dbInstance.isInitialized) {
                    dbInstance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "plant_saver_db"
                    ).build()
                }
            }
            return dbInstance
        }
    }

}

