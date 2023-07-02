package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AllSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableViewHolder

class ComposableFactory {

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoFrameBinding.inflate(inflater, parent, false)
        val composableType = ComposableTypeSet.values()[viewType]
        return ComposableViewHolder(binding, composableType)
    }

    fun getItemType(viewModel: ViewModel): Int {
        return when (viewModel) {
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
}