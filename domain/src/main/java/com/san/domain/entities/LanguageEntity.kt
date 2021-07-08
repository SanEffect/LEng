package com.san.domain.entities

import androidx.room.Entity
import java.util.*

@Entity(tableName = "languages")
data class LanguageEntity(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
)
