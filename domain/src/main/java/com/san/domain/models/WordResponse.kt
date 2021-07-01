package com.san.domain.models

// --- Word Result model

data class WordResponse (
    val word: String,
    val frequency: Float,
    val syllables: Syllables,
    val pronunciation: Pronunciation,
    val results: List<WordResult>
)

data class WordResult (
    val definition: String,
    val partOfSpeech: String,
    val synonyms: List<String>?,
    val typeOf: List<String>?,
    val partOf: List<String>?,
    val hasTypes: List<String>?,
    val hasParts: List<String>?,
    val examples: List<String>?,
    val verbGroup: List<String>?,
    val derivation: List<String>?,
)

data class Syllables (
    val count: Int,
    val list: List<String>
)
data class Pronunciation(
    val all: String
)

// --- Word Definition model

data class WordDefinition (
    val word: String,
    val definitions: List<WordDefinitions>
)

data class WordDefinitions(val definition: String)
