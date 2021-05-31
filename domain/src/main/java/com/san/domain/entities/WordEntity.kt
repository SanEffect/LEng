package com.san.domain.entities

import androidx.room.Entity

@Entity(tableName = "words")
data class WordEntity (
    val name: String,
    val definitions: List<WordDefinition>,
    val synonyms: List<String>,
    val languageId: String,
)