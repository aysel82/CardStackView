package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.example.myapplication.R
import com.example.myapplication.data.adapter.AnimeAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.viewModel.MainViewModel
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction
import com.yuyakaido.android.cardstackview.SwipeableMethod
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CardStackListener {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    private lateinit var adapter: AnimeAdapter
    private lateinit var layoutManager: CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        layoutManager = CardStackLayoutManager(this, this).apply {
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
        }

        cardStackView.layoutManager = layoutManager

        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }

        val count = viewModel.getAnimeCount()
        if (count > 0) {
            //if anime list  exists in room db, get list from room db.
            viewModel.animeListFromDb.observe(this, Observer {
                it?.let {
                    viewModel.animeListLiveData.value = it
                }
            })
        } else {
            //if anime list doesn't exist in room db, get list from service.
            viewModel.getAnimeList()
        }

        viewModel.animeListLiveData.observe(this, Observer {
            it?.let { list ->
                adapter = AnimeAdapter(list)
                cardStackView.adapter = adapter

                if (count == 0) {
                    viewModel.insertAnimeListToRoomDb(list) {
                        Log.i("TAG_LIST", list.toString())
                    }
                }
            }
        })
    }

    override fun onCardDisappeared(view: View?, position: Int) {
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {
    }

    override fun onCardSwiped(direction: Direction?) {
    }

    override fun onCardCanceled() {
    }

    override fun onCardAppeared(view: View?, position: Int) {
    }

    override fun onCardRewound() {
    }
}