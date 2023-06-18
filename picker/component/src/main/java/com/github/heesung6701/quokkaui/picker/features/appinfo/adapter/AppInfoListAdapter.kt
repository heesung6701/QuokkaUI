package com.github.heesung6701.quokkaui.picker.features.appinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.AppInfoViewHolder
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoBinding

class AppInfoListAdapter : ListAdapter<AppInfoViewModel, AppInfoViewHolder>(DiffUtils) {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppInfoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoBinding.inflate(inflater, parent, false)
        return AppInfoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: AppInfoViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: AppInfoViewHolder, position: Int) {
        val viewModel = getItem(position)
        holder.bindData(viewModel)
    }
}