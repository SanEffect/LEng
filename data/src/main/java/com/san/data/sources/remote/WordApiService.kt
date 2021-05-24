package com.san.data.sources.remote

import com.san.domain.entities.WordResult
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Path

interface WordApiService {

    @GET("words/{word}/definitions")
    fun getWordDefinitionsAsync(@Path("word") word: String) : Deferred<WordResult>
}