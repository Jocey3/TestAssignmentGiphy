package com.dev.jocey.testgiphy.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.jocey.testgiphy.R
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity

class GifsPagingAdapter : PagingDataAdapter<GiphyEntity, GifsPagingAdapter.QuoteViewHolder>(
    COMPARATOR
) {

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val quote = itemView.findViewById<TextView>(R.id.title_gif)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gif, parent, false)
        return QuoteViewHolder(view)
    }


    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val item = getItem(position)
        holder.quote.text = item!!.title
    }


    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GiphyEntity>() {
            override fun areItemsTheSame(oldItem: GiphyEntity, newItem: GiphyEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GiphyEntity, newItem: GiphyEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}