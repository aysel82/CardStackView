package com.example.myapplication.data.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.db.entity.Anime
import com.example.myapplication.databinding.LayoutCardViewAnimeBinding

class AnimeAdapter(
    private val values: List<Anime>
) : RecyclerView.Adapter<AnimeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_card_view_anime,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        values?.let {
            holder.binding.anime = it[position]
            holder.binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(val binding: LayoutCardViewAnimeBinding) :
        RecyclerView.ViewHolder(binding.root)
}