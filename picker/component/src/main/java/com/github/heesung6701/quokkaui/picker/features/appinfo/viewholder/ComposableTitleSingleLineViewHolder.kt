package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoTitleSingleLineBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

class ComposableTitleSingleLineViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoTitleSingleLineBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is AppInfoViewModel) {
            binding.appName.text = viewModel.appName
        }
    }
}