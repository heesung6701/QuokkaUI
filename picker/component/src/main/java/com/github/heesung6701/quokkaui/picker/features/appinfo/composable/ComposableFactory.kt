package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AllSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableViewHolder

class ComposableFactory {

    companion object {
        private const val BIT_PER_FRAME = 4
        private const val FRAME_COUNT = 4
        const val TOTAL_BIT = BIT_PER_FRAME * FRAME_COUNT
    }

    @IntRange(from = 0, to = (1 shl TOTAL_BIT).toLong())
    annotation class ComposableViewType

    val idRange = 0 until (1 shl TOTAL_BIT)

    fun onCreateViewHolder(parent: ViewGroup, @ComposableViewType viewType: Int): ComposableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoFrameBinding.inflate(inflater, parent, false)
        val composableType = ComposableTypeSet.values()[viewType]
        return ComposableViewHolder(binding, composableType)
    }

    @ComposableViewType
    fun getItemType(viewModel: ViewModel): Int? {
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
                null
            }
        }
    }
}