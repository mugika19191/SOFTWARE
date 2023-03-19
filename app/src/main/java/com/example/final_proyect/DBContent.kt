package com.example.final_proyect

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Content::class],
    version = 1
)

abstract class DBContent:RoomDatabase() {
    abstract fun daoContent():DaoContent
}