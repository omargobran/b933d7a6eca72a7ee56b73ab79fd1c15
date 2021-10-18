package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.databinding.ItemViewPagerBinding
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.Spaceship
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.holder.ViewPagerHolder
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.FilterErrorCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.ViewPagerCallback
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util.ViewPagerFilter

class ViewPagerAdapter constructor(
    private val callback: ViewPagerCallback,
    private val filterCallback: FilterErrorCallback,
    private var spaceship: Spaceship
) : RecyclerView.Adapter<ViewPagerHolder>(), Filterable {

    var allItems: List<SpaceStation> = arrayListOf()

    var items: List<SpaceStation> = arrayListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerHolder {
        return ViewPagerHolder(
            ItemViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            callback
        )
    }

    override fun onBindViewHolder(holder: ViewPagerHolder, position: Int) {
        holder.bind(items[position], spaceship)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getFilter(): Filter {
        return ViewPagerFilter(allItems, this, filterCallback)
    }

    fun updateSpaceStation(position: Int, spaceStation: SpaceStation) {
        items.toMutableList()[position] = spaceStation
        notifyDataSetChanged()
    }

    fun updateSpaceship(spaceship: Spaceship) {
        this.spaceship = spaceship
        removeFirstItem()
    }

    fun removeFirstItem() {
        items = items.drop(0)
        allItems = ArrayList(items)
        notifyDataSetChanged()
    }
}