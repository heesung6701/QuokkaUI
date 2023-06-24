package com.github.heesung6701.quokkaui.picker.features.appinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable.ComposableTypeSet
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableViewHolder
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

class AppInfoListAdapter(val onItemClick: (AppInfo) -> Unit) : ListAdapter<ViewModel, ComposableViewHolder>(DiffUtils) {

    companion object {
        val DiffUtils = object : DiffUtil.ItemCallback<ViewModel>() {
            override fun areItemsTheSame(
                oldItem: ViewModel,
                newItem: ViewModel
            ): Boolean {
                return oldItem.key == newItem.key
            }

            override fun areContentsTheSame(
                oldItem: ViewModel,
                newItem: ViewModel
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
            if (item is AppInfoViewModel || item is AppInfoSubTitleViewModel) {
                onItemClick(item.key as AppInfo)
            }
        }
    }

    override fun onBindViewHolder(holder: ComposableViewHolder, position: Int) {
        val viewModel = getItem(position)
        holder.bindData(viewModel)
    }

    override fun getItemViewType(position: Int): Int {

        return when(getItem(position)) {
            is AppInfoSwitchViewModel -> {
                ComposableTypeSet.SwitchPreference.ordinal
            }
            is AppInfoSubTitleViewModel -> {
                ComposableTypeSet.TwoTextLine.ordinal
            }
            else -> {
                ComposableTypeSet.SingleTextLine.ordinal
            }
        }
    }
}