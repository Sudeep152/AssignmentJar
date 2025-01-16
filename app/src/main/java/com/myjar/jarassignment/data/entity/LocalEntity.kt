package com.myjar.jarassignment.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LocalEntity(
    @PrimaryKey val id: String,
    val name: String,
)
