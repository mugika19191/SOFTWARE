package com.example.final_proyect;


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoContent {

    @Query("Select * from Content")
    suspend fun getContents(): List<Content>

    @Query("Select * from Content WHERE name=:name")
    suspend fun getByName(name:String):Content

    @Update
    suspend fun update(content: Content)
    @Insert
    suspend fun insert(content: Content)
    @Delete
    suspend fun delete(content: Content)
}
