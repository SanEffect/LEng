package com.san.data.sources.remote

import com.san.domain.models.WordDefinitions
import com.san.domain.models.WordResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface WordApiService {

    @GET("words/{word}")
    fun getWordAsync(@Path("word") word: String): Deferred<WordResponse>

    @GET("words/{word}/definitions")
    fun getWordDefinitionsAsync(@Path("word") word: String): Deferred<WordDefinitions>
}
