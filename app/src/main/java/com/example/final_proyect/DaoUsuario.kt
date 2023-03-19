package com.example.final_proyect;


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoUsuario {

    @Query("Select * from Usuario")
    fun getUsers(): List<Usuario>

    @Query("Select * from Usuario WHERE name=:name")
    suspend fun getByName(name:String):Usuario

    @Update
    suspend fun update(usuario:Usuario)
    @Insert
    suspend fun insert(usuario:Usuario)
    @Delete
    suspend fun delete(usuario: Usuario)
}
