package com.github.heesung6701.quokkaui.picker.features.appinfo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Switch
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable.ComposableTypeSet
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableViewHolder
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AllSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasSwitch
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class AppInfoListAdapter() :
    ListAdapter<ViewModel, ComposableViewHolder>(DiffUtils) {

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
                return oldItem == newItem
            }
        }
    }

    private var showAllApps: Boolean = false
    private var allSwitchViewModel: AllSwitchViewModel? = null
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
        val onItemClicked = item.onItemClicked

        if (onItemClicked == null) {
            if (item is HasSwitch) {
                holder.itemView.setOnClickListener {
                    val prevValue = holder.itemView.findViewById<Switch>(R.id.switch_activate).isChecked
                    runBlocking {
                        item.activateFlow.emit(!prevValue)
                    }
                }
            }
        } else {
            if (item.key is AppInfo) {
                holder.itemView.setOnClickListener {
                    onItemClicked(item.key as AppInfo)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ComposableViewHolder, position: Int) {
        val viewModel = getItem(position)
        holder.bindData(viewModel)
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is AllSwitchViewModel -> {
                ComposableTypeSet.AllSwitch.ordinal
            }

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

    override fun submitList(list: MutableList<ViewModel>?) {
        if (list == null) {
            return super.submitList(null)
        }
        val newItemList = mutableListOf<ViewModel>()
        if (showAllApps) {
            allSwitchViewModel?.let {
                newItemList.add(it)
            }
        }
        newItemList.addAll(list)
        super.submitList(newItemList)
    }
    fun setShowAllApps(showAllApps: Boolean) {
        this.showAllApps = showAllApps
        allSwitchViewModel = if (showAllApps) {
            AllSwitchViewModel(MutableStateFlow(false).apply {
                val activateFlow = this
                CoroutineScope(Dispatchers.Main.immediate).launch {
                    activateFlow.collect { allCheckState ->
                        currentList.map {
                            if (it is HasSwitch) {
                                it.activateFlow.emit(allCheckState)
                            }
                        }
                    }
                }
            })
        } else null
        notifyItemInserted(0)
    }
}