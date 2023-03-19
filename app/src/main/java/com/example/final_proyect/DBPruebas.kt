package com.example.final_proyect

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Usuario::class],
    version = 1
)

abstract class DBPruebas:RoomDatabase() {
    abstract fun daoUsuario():DaoUsuario
}