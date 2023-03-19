package com.example.final_proyect

import androidx.annotation.NonNull
import androidx.room.*
import java.net.URI

@Entity
data class Content (
    @PrimaryKey
    val name: String,
    val creator: String,
    val studio: String,
    val desc: String,
    val episodes: Int,
    val date: String)