package com.dev.jocey.testgiphy.presentation.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.databinding.ItemGifBinding

class GifsPagingAdapter : PagingDataAdapter<GiphyEntity, GifsPagingAdapter.GifViewHolder>(
    COMPARATOR
) {
    var adapterClickListener: AdapterClickListener? = null

    inner class GifViewHolder(private val binding: ItemGifBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindGif(gif: GiphyEntity) {
            binding.apply {
                titleGif.text = gif.title
                Glide.with(this.root)
                    .load(gif.url)
                    .into(gifImage)
                deleteGif.setOnClickListener {
                    adapterClickListener?.onGifDeleted(gif)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding =
            ItemGifBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GifViewHolder(binding)
    }


    override fun onBindViewHolder(holder: GifViewHolder, position: Int) {
        getItem(position)?.let { holder.bindGif(it) }
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

    interface AdapterClickListener {
        fun onGifClicked(gif: GiphyEntity?)
        fun onGifDeleted(gif: GiphyEntity?)
    }
}