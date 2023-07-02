package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.icon

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoIconBinding
import com.github.heesung6701.quokkaui.picker.extend.loadIcon
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasAppIcon
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder

class ComposableIconViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoIconBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is HasAppIcon) {
            binding.appIcon.loadIcon(viewModel.appIcon)
        }
    }

    override fun onViewRecycled() {}
}