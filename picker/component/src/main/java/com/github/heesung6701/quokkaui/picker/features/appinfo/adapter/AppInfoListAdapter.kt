package com.github.heesung6701.quokkaui.picker.features.appinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable.ComposableTypeSet
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableViewHolder
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel

class AppInfoListAdapter(val onItemClick: (AppInfoViewModel) -> Unit) : ListAdapter<AppInfoViewModel, ComposableViewHolder>(DiffUtils) {

    companion object {
        val DiffUtils = object : DiffUtil.ItemCallback<AppInfoViewModel>() {
            override fun areItemsTheSame(
                oldItem: AppInfoViewModel,
                newItem: AppInfoViewModel
            ): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(
                oldItem: AppInfoViewModel,
                newItem: AppInfoViewModel
            ): Boolean {
                return oldItem.key == newItem.key
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoFrameBinding.inflate(inflater, parent, false)
        val composableType = ComposableTypeSet.values()[viewType]
        return ComposableViewHolder(binding, composableType)
    }

    override fun onBindViewHolder(
        holder: ComposableViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
        val item = getItem(position)
        holder.bindData(item)
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun onBindViewHolder(holder: ComposableViewHolder, position: Int) {
        val viewModel = getItem(position)
        holder.bindData(viewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return ComposableTypeSet.SingleTextLine.ordinal
    }
}