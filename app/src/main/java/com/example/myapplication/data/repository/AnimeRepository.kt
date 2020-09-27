package com.example.myapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.api.ServiceAPI
import com.example.myapplication.data.db.AnimeDao
import com.example.myapplication.data.db.AnimeDatabase
import com.example.myapplication.data.db.AnimeList
import com.example.myapplication.data.db.entity.Anime
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class AnimeRepository(
    application: Application
) {
    private var database: AnimeDatabase =
        AnimeDatabase.getDatabase(
            application
        )

    private var animeDao: AnimeDao

    init {
        animeDao = database.animeDao()
    }

    val animeListLiveData = MutableLiveData<List<Anime>>()

    val animeListFromDb: LiveData<List<Anime>> = animeDao.getAnimeList()

    //get anime count from room db.
    fun getAnimeCount(): Int {
        val callable = Callable {
            animeDao.getAnimeCount()
        }
        val future = Executors.newSingleThreadExecutor().submit(callable)
        return future.get().toInt()
    }

    //insert anime into room db
    fun insertAnimeToDb(anime: Anime): Long {
        val callable = Callable {
            animeDao.insertAnimeToDb(anime)
        }
        val future = Executors.newSingleThreadExecutor().submit(callable)
        return future.get().toLong()
    }

    //get anime list from service.
    fun getAnimeList() {
        ServiceAPI().getAnimeList().enqueue(object : Callback<AnimeList> {
            override fun onFailure(call: Call<AnimeList>, t: Throwable) {
                animeListLiveData.value = null
            }

            override fun onResponse(call: Call<AnimeList>, response: Response<AnimeList>) {
                response.body()?.let {
                    animeListLiveData.value = it.results
                }
            }
        })
    }

}