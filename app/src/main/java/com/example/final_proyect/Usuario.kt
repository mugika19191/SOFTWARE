package com.example.final_proyect

import androidx.annotation.NonNull
import androidx.room.*
import java.net.URI

@Entity
data class Usuario (
    @PrimaryKey
    val name: String,
    val pass: String,
    val img: String
    )