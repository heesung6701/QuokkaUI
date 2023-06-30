package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.title

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoTitleSingleLineBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasTitle
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder

class ComposableTitleSingleLineViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoTitleSingleLineBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is HasTitle) {
            binding.appName.text = viewModel.title
        }
    }
}