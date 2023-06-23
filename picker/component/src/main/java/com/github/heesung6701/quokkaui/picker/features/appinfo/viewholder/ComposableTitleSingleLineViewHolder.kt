package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoTitleSingleLineBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel

class ComposableTitleSingleLineViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoTitleSingleLineBinding.bind(itemView)

    override fun bindData(viewModel: AppInfoViewModel) {
        binding.appName.text = viewModel.appName
    }
}