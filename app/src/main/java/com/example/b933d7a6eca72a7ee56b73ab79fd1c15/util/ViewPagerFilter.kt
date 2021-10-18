package com.example.b933d7a6eca72a7ee56b73ab79fd1c15.util

import android.widget.Filter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.domain.model.SpaceStation
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.adapter.ViewPagerAdapter
import com.example.b933d7a6eca72a7ee56b73ab79fd1c15.ui.callback.FilterErrorCallback
import java.util.*
import kotlin.collections.ArrayList

class ViewPagerFilter constructor(
    private var originalItems: List<SpaceStation>,
    private val adapter: ViewPagerAdapter,
    private val callback: FilterErrorCallback,
) : Filter() {

    override fun performFiltering(charSequence: CharSequence): FilterResults {
        val filteredItems: List<SpaceStation> = originalItems.filter {
            it.name.lowercase(Locale.getDefault()).trim().contains(charSequence)
        }

        return FilterResults().apply {
            values = filteredItems
            count = filteredItems.size
        }
    }

    override fun publishResults(charSequence: CharSequence, filterResults: FilterResults?) {
        if (filterResults != null) {
            val filteredItems = filterResults.values as List<SpaceStation>
            if (filteredItems.isNullOrEmpty()) {
                filterErrorAction()
            } else {
                adapter.items = filteredItems
            }
        } else {
            filterErrorAction()
        }
    }

    private fun filterErrorAction() {
        adapter.items = ArrayList()
        callback.onFilterError()
    }

}