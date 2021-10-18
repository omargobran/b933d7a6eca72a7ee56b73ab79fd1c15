package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.holder;

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.ItemRecyclerViewBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Distance
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.RecyclerViewCallback

class RecyclerViewHolder constructor(
    private val binding: ItemRecyclerViewBinding,
    private val callback: RecyclerViewCallback,
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
            this.onClickListener = this@RecyclerViewHolder
        }
    }

    override fun onClick(view: View) {
        val isSuccess = callback.onFavoriteButtonClick(bindingAdapterPosition)
        if (isSuccess) {
            updateSpaceStation()
        }
    }

    private fun updateSpaceStation() {
        spaceStation.isFavorite = !spaceStation.isFavorite
        binding.spaceStation = spaceStation
    }
}