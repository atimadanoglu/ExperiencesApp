package com.atakanmadanoglu.experiencesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atakanmadanoglu.experiencesapp.data.Experience
import com.atakanmadanoglu.experiencesapp.databinding.ExperienceItemBinding

class HomePageAdapter(
    private val clickListener: (experience: Experience) -> Unit
): ListAdapter<Experience, HomePageAdapter.ExperienceViewHolder>(ExperienceDiffUtil()) {
    class ExperienceViewHolder(private val binding: ExperienceItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ExperienceViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ExperienceItemBinding.inflate(layoutInflater, parent, false)
                return ExperienceViewHolder(binding)
            }
        }
        fun bind(experience: Experience, clickListener: (experience: Experience) -> Unit) {
            binding.root.setOnClickListener { clickListener(experience) }
            binding.itemImageView.setImageBitmap(experience.pictureBitmap)
            binding.title.text = experience.title
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExperienceViewHolder {
        return ExperienceViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(holder: ExperienceViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class ExperienceDiffUtil: DiffUtil.ItemCallback<Experience>() {
    override fun areItemsTheSame(oldItem: Experience, newItem: Experience): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Experience, newItem: Experience): Boolean {
        return oldItem == newItem
    }
}