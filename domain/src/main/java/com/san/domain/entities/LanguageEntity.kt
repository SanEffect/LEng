package com.san.domain.entities

import androidx.room.Entity

@Entity(tableName = "languages")
data class LanguageEntity(
    val id: String,
    val name: String,
)
