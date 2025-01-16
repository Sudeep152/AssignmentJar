package com.myjar.jarassignment.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)
