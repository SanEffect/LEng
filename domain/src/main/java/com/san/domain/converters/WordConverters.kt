package com.san.domain.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.san.domain.entities.WordEntity
import com.san.domain.models.Syllables
import com.san.domain.models.WordResponse
import com.san.domain.models.WordResult
import java.lang.reflect.Type

class WordConverters {

//    @TypeConverter
//    fun fromStringToWordEntity(data: String?): WordEntity? {
//        if (data == null) return null
//
//        val listType: Type = object : TypeToken<WordEntity?>() {}.type
//        return Gson().fromJson(data, listType)
//    }
//
//    @TypeConverter
//    fun fromWordEntityToString(wordEntity: WordEntity): String = Gson().toJson(wordEntity)


    @TypeConverter
    fun fromStringToWordResponse(data: String?): WordResponse? {
        if (data == null) return null

        val type: Type = object : TypeToken<WordResponse?>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromWordResponseToString(wordResponse: WordResponse): String = Gson().toJson(wordResponse)


    // ---------------------------------

    @TypeConverter
    fun fromStringToWordResult(data: String?): WordResult? {
        if (data == null) return null

        val type: Type = object : TypeToken<WordResult?>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromWordResultToString(wordResult: WordResult): String = Gson().toJson(wordResult)

    // ---------------------------------

    @TypeConverter
    fun fromStringToSyllables(data: String?): Syllables? {
        if (data == null) return null

        val type: Type = object : TypeToken<Syllables?>() {}.type
        return Gson().fromJson(data, type)
    }

    @TypeConverter
    fun fromSyllablesToString(syllables: Syllables): String = Gson().toJson(syllables)
}
