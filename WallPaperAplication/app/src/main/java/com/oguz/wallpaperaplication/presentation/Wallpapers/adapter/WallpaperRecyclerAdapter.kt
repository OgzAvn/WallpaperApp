package com.oguz.wallpaperaplication.presentation.Wallpapers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.oguz.wallpaperaplication.databinding.WallpapersRecyclerviewBinding
import com.oguz.wallpaperaplication.domain.model.wallpaper
import com.oguz.wallpaperaplication.presentation.Wallpapers.view.WallpaperFragmentDirections
import javax.inject.Inject


class WallpaperRecyclerAdapter @Inject constructor(
    val glide : RequestManager
) : RecyclerView.Adapter<WallpaperRecyclerAdapter.wallpaperHolder>(){

    class wallpaperHolder(val binding : WallpapersRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root){

    }

    private val diffUtil = object : DiffUtil.ItemCallback<wallpaper>(){
        override fun areItemsTheSame(oldItem: wallpaper, newItem: wallpaper): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: wallpaper, newItem: wallpaper): Boolean {
            return oldItem == newItem
        }


    }

    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)

    var wallpaperList : List<wallpaper>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): wallpaperHolder {
        val binding = WallpapersRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return wallpaperHolder(binding)
    }

    override fun getItemCount(): Int {
        return wallpaperList.size
    }

    override fun onBindViewHolder(holder: wallpaperHolder, position: Int) {

        glide.load(wallpaperList[position].previewURL).into(holder.binding.wallpaperImageView)

        holder.itemView.setOnClickListener {

            val action = WallpaperFragmentDirections.actionWallpaperFragmentToWallpaperDetailFragment(wallpaperList[position].id.toString())
            it.findNavController().navigate(action)

        }



    }
}