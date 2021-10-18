package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.ItemRecyclerViewBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.holder.RecyclerViewHolder
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.RecyclerViewCallback

class RecyclerViewAdapter constructor(
    private val callback: RecyclerViewCallback,
    private val spaceship: Spaceship
) : RecyclerView.Adapter<RecyclerViewHolder>() {

    var items: List<SpaceStation> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            ItemRecyclerViewBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback
        )
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(items[position], spaceship)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateSpaceStation(position: Int, spaceStation: SpaceStation) {
        items.toMutableList()[position] = spaceStation
        notifyDataSetChanged()
    }
}