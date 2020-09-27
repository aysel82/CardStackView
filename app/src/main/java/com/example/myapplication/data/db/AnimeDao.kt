package com.example.myapplication.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.data.db.entity.Anime

@Dao
interface AnimeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) ///this line necessary for avoid duplicate record.
    fun insertAnimeToDb(anime: Anime): Long

    @Query("SELECT * FROM anime_data_table")
    fun getAnimeList(): LiveData<List<Anime>>

    @Query("SELECT COUNT(*) FROM anime_data_table")
    fun getAnimeCount(): Int
}