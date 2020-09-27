package com.example.myapplication.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "anime_data_table"
)
data class Anime(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "score")
    val score: String,

    @ColumnInfo(name = "episodes")
    val episodes: Int,

    @ColumnInfo(name = "image_url")
    val image_url: String,

    @ColumnInfo(name = "synopsis")
    val synopsis: String
)