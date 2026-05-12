package com.minigrocery.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minigrocery.app.databinding.ItemBannerBinding
import com.minigrocery.app.utils.loadImage

/**
 * RecyclerView adapter for banners
 */
class BannerAdapter : ListAdapter<Banner, BannerAdapter.BannerViewHolder>(BannerDiffCallback()) {
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BannerViewHolder(binding)
    }
    
    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
    
    inner class BannerViewHolder(
        private val binding: ItemBannerBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        
        fun bind(banner: Banner) {
            binding.apply {
                ivBanner.loadImage(banner.imageUrl)
                tvBannerTitle.text = banner.title
                tvBannerSubtitle.text = banner.subtitle
            }
        }
    }
    
    private class BannerDiffCallback : DiffUtil.ItemCallback<Banner>() {
        override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem.id == newItem.id
        }
        
        override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
            return oldItem == newItem
        }
    }
}
