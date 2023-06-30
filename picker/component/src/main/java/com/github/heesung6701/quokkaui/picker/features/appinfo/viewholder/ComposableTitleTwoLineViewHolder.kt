package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoTitleTwoLineBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasSubTitle
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasTitle
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder

class ComposableTitleTwoLineViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoTitleTwoLineBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is HasTitle) {
            binding.appName.text = viewModel.title
        }
        if (viewModel is HasSubTitle) {
            binding.subTitle.text = viewModel.subTitle
        }
    }
}