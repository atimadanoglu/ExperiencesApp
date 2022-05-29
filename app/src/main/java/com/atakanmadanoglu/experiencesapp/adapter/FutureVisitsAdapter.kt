package com.atakanmadanoglu.experiencesapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.atakanmadanoglu.experiencesapp.data.FutureVisit
import com.atakanmadanoglu.experiencesapp.databinding.FutureVisitItemBinding

class FutureVisitsAdapter(
    private val clickListener: (futureVisit: FutureVisit, isChecked: Boolean) -> Unit
): ListAdapter<FutureVisit, FutureVisitsAdapter.FutureVisitViewHolder>(FutureVisitDiffUtil()) {
    class FutureVisitViewHolder(private val binding: FutureVisitItemBinding): RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): FutureVisitViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = FutureVisitItemBinding.inflate(layoutInflater, parent, false)
                return FutureVisitViewHolder(binding)
            }
        }
        fun bind(futureVisit: FutureVisit, clickListener: (futureVisit: FutureVisit, isChecked: Boolean) -> Unit) {
            binding.futureVisit = futureVisit
            binding.isDone.setOnCheckedChangeListener { _, b ->
                clickListener(futureVisit, b)
            }
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FutureVisitViewHolder {
        return FutureVisitViewHolder.inflateFrom(parent)
    }

    override fun onBindViewHolder(
        holder: FutureVisitViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }
}

class FutureVisitDiffUtil: DiffUtil.ItemCallback<FutureVisit>() {
    override fun areItemsTheSame(oldItem: FutureVisit, newItem: FutureVisit): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FutureVisit, newItem: FutureVisit): Boolean {
        return oldItem == newItem
    }
}
