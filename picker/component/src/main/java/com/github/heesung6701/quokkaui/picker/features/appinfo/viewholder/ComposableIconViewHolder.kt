package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoIconBinding
import com.github.heesung6701.quokkaui.picker.extend.loadIcon
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

class ComposableIconViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoIconBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is AppInfoViewModel) {
            binding.appIcon.loadIcon(viewModel.appIcon)
        }
        if (viewModel is AppInfoSubTitleViewModel) {
            binding.appIcon.loadIcon(viewModel.appIcon)
        }
        if (viewModel is AppInfoSwitchViewModel) {
            binding.appIcon.loadIcon(viewModel.appIcon)
        }
    }
}