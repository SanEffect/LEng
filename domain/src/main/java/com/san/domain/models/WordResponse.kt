package com.san.domain.models

import android.os.Parcelable
import com.san.domain.entities.WordEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

// --- Word Result model

@Parcelize
data class WordResponse (
    val word: String,
    val frequency: Float,
    val syllables: Syllables,
    val pronunciation: Pronunciation,
    val results: List<WordResult>
) : Parcelable {

    fun toEntity(): WordEntity {
        return WordEntity(
            id = UUID.randomUUID().toString(),
            name = word,
            data = this
        )
    }
}

@Parcelize
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
) : Parcelable

@Parcelize
data class Syllables (
    val count: Int,
    val list: List<String>
) : Parcelable

@Parcelize
data class Pronunciation(
    val all: String
) : Parcelable

// --- Word Definition model

data class WordDefinition (
    val word: String,
    val definitions: List<WordDefinitions>
)

data class WordDefinitions(val definition: String)
