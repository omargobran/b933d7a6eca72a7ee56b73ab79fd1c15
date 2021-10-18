package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.holder

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.ItemViewPagerBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Distance
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.ViewPagerCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.Constants.TAG

class ViewPagerHolder constructor(
    private val binding: ItemViewPagerBinding,
    private val callback: ViewPagerCallback
) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    private lateinit var spaceStation: SpaceStation
    private lateinit var spaceship: Spaceship

    fun bind(spaceStation: SpaceStation, spaceship: Spaceship) {
        val distance = Distance(spaceship.currentStation!!.coordinate, spaceStation.coordinate)
        this.spaceStation = spaceStation
        this.spaceship = spaceship
        binding.apply {
            this.spaceStation = spaceStation
            this.spaceship = spaceship
            this.distance = distance
            this.onClickListener = this@ViewPagerHolder
        }
    }

    override fun onClick(view: View) {
        if (view == binding.travelButton) {
            callback.onTravelButtonClick(bindingAdapterPosition)
        } else if (view == binding.favoriteButton) {
            val isSuccess = callback.onFavoriteButtonClick(bindingAdapterPosition)
            if (isSuccess) {
                updateSpaceStation()
            }
        }
    }

    private fun updateSpaceStation() {
        spaceStation.isFavorite = !spaceStation.isFavorite
        binding.spaceStation = spaceStation
    }
}