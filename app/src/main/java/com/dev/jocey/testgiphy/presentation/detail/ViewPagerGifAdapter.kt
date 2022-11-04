package com.dev.jocey.testgiphy.presentation.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.jocey.testgiphy.data.local.entity.GiphyEntity
import com.dev.jocey.testgiphy.databinding.ItemGifViewPagerBinding

class ViewPagerGifAdapter : PagingDataAdapter<GiphyEntity, ViewPagerGifAdapter.GifViewHolder>(
    COMPARATOR
) {

    inner class GifViewHolder(private val binding: ItemGifViewPagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindGif(gif: GiphyEntity) {
            binding.apply {
                titleGif.text = gif.title
                Glide.with(this.root)
                    .load(gif.url)
                    .into(gifImage)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifViewHolder {
        val binding =
            ItemGifViewPagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

}