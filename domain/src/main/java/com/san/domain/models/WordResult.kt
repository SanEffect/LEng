package com.san.domain.entities

data class WordResult (
    val word: String,
    val definitions: List<WordDefinition>
)

data class WordDefinition(
    val definition: String
)