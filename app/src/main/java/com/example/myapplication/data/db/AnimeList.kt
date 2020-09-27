package com.example.myapplication.data.db

import com.example.myapplication.data.db.entity.Anime

data class AnimeList(
    val results: List<Anime>,
    val last_page: Int
)