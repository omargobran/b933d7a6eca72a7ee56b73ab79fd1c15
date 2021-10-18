package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.favorites

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.FragmentFavoritesBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.RecyclerViewAdapter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.RecyclerViewCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.State
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), RecyclerViewCallback {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        favoritesViewModel.spaceship.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> Log.d(Constants.TAG, "spaceship: Loading...")
                is State.Error -> {
                    Log.e(Constants.TAG, "spaceship: Error", it.exception)
                }
                is State.Success -> handleSpaceship(it.data)
            }
        })

        favoritesViewModel.getSpaceship()

        favoritesViewModel.favoriteSpaceStations.observe(viewLifecycleOwner, {
            when (it) {
                is State.Loading -> Log.d(Constants.TAG, "favoriteSpaceStations: Loading...")
                is State.Error -> {
                    Log.e(Constants.TAG, "favoriteSpaceStations: Error", it.exception)
                }
                is State.Success -> handleFavoriteStations(it.data)
            }
        })

        return binding.root
    }

    private fun handleSpaceship(data: Spaceship) {
        initRecyclerView(data)
        favoritesViewModel.getFavoriteSpaceStations()
    }

    private fun handleFavoriteStations(data: List<SpaceStation>) {
        recyclerViewAdapter.items = data
    }

    private fun initRecyclerView(spaceship: Spaceship) {
        recyclerViewAdapter = RecyclerViewAdapter(this, spaceship)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recyclerViewAdapter
        }
    }

    override fun onFavoriteButtonClick(position: Int): Boolean {
        val spaceStation = recyclerViewAdapter.items[position]
        val isUpdateSuccessful =
            favoritesViewModel.favoriteSpaceStation(recyclerViewAdapter.items[position])
        if (!isUpdateSuccessful) {
            Toast.makeText(context, "Uzay İstasyonu Güncellenemedi!", Toast.LENGTH_LONG).show()
        } else {
            spaceStation.isFavorite = !spaceStation.isFavorite
            recyclerViewAdapter.updateSpaceStation(position, spaceStation)
        }
        return isUpdateSuccessful
    }
}