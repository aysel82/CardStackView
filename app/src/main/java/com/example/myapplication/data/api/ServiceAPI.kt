package com.example.myapplication.data.api

import com.example.myapplication.data.db.AnimeList
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ServiceAPI {

    @GET("v3/search/anime?q=naruto/")
    fun getAnimeList(): Call<AnimeList>

    companion object {
        operator fun invoke(): ServiceAPI {
            return Retrofit.Builder()
                .baseUrl("https://api.jikan.moe/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ServiceAPI::class.java)
        }
    }
}