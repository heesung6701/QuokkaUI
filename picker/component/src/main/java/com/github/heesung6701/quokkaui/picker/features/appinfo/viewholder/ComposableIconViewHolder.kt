package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoIconBinding
import com.github.heesung6701.quokkaui.picker.extend.loadIcon
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel

class ComposableIconViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoIconBinding.bind(itemView)

    override fun bindData(viewModel: AppInfoViewModel) {
        binding.appIcon.loadIcon(viewModel.appIcon)
    }
}