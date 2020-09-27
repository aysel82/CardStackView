package com.example.myapplication.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.entity.Anime
import com.example.myapplication.data.repository.AnimeRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var animeRepository: AnimeRepository = AnimeRepository(application)

    val animeListLiveData = animeRepository.animeListLiveData

    val animeListFromDb: LiveData<List<Anime>> = animeRepository.animeListFromDb

    fun getAnimeCount(): Int {
        return animeRepository.getAnimeCount()
    }

    fun getAnimeList() {
        animeRepository.getAnimeList()
    }

    /**
     * Room Database related functions...
     */
    //START - Room Database related functions...

    fun insertAnimeListToRoomDb(
        animeList: List<Anime>,
        callback: (result: Boolean) -> Unit
    ) =
        viewModelScope.launch {
            var result = true
            animeList.forEach { it ->
                val insertedAnimeId = animeRepository.insertAnimeToDb(it)
                if (insertedAnimeId < 0) {
                    result = false
                }
            }
            if (result)
                callback.invoke(true)
        }

    //END - Room Database related functions...
}