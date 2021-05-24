package com.san.leng.core.di.modules

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.san.data.repositories.WordsApiRepository
import com.san.domain.repositories.IWordsApiRemoteRepository
import com.san.data.sources.remote.IWordsRemoteDataSource
import com.san.data.sources.remote.WordApiService
import com.san.leng.core.Constants
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class WordsApiModule {

    private val BASE_URL = Constants.WORDS_API_URL

    @Provides
    @Singleton
    fun provideWordsService(retrofit: Retrofit) : WordApiService = retrofit.create(WordApiService::class.java)

//    @Provides
//    @Singleton
//    fun provideRemoteDataSource(wordsService: WordApiService) : IWordsRemoteDataSource = WordsRemoteDataSource(wordsService)

    @Provides
    @Singleton
    fun provideWordsApiRemoteRepository(wordsService: WordApiService) : IWordsApiRemoteRepository = WordsApiRepository(wordsService)

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient) : Retrofit {

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttp(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {

        return OkHttpClient.Builder()
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .callTimeout(1, TimeUnit.MINUTES)
            .addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .header("x-rapidapi-key", Constants.WORDS_API_KEY)
                    .header("x-rapidapi-host", Constants.WORDS_API_HOST)

                val request = requestBuilder.build()
                chain.proceed(request)
            }
            .build()
    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}