
package com.example.android.marsrealestate.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.MarsProperty

class PhotoGridAdapter(private val onClickListener: ClickListener) : ListAdapter<MarsProperty,PhotoGridAdapter.MarsPropView>(DiffCall){

    class MarsPropView(private var binding: GridViewItemBinding)
        :RecyclerView.ViewHolder(binding.root){

        fun bind(mars:MarsProperty){
            binding.marsProp = mars
            binding.executePendingBindings()
        }

    }

    companion object DiffCall:DiffUtil.ItemCallback<MarsProperty>() {
        override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            // === means that both objects are the same reference
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
            return oldItem.id == newItem.id
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsPropView {
        return MarsPropView(GridViewItemBinding
                .inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MarsPropView, position: Int) {
        val marsProperty = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(marsProperty)
        }
        holder.bind(marsProperty)
    }
    class ClickListener(val clickListener: (mars:MarsProperty)->Unit){
        fun onClick(mars: MarsProperty) = clickListener(mars)
    }
}
