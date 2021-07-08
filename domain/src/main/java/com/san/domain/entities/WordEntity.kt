package com.san.domain.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.san.domain.converters.WordConverters
import com.san.domain.dataTransferObjects.RecordDto
import com.san.domain.models.WordResponse
import java.util.*

@Entity(tableName = "words")
data class WordEntity(

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val name: String,

    @TypeConverters(WordConverters::class)
    val data: WordResponse
) {
    companion object {
        fun toEntity(word: WordResponse): WordEntity {
            return WordEntity(
                id = UUID.randomUUID().toString(),
                name = word.word,
                data = word
            )
        }
    }

    fun toDomain(): WordResponse {
        return WordResponse(
            word = name,
            frequency = data.frequency,
            syllables = data.syllables,
            pronunciation = data.pronunciation,
            results = data.results
        )
    }
}



/*
@Entity(tableName = "words")
data class WordEntity(

    @PrimaryKey(autoGenerate = true)
    val id: String = UUID.randomUUID().toString(),
    val languageId: String,
    val name: String,
    val definitions: List<WordDefinition>,
    val synonyms: List<String>,
)*/
