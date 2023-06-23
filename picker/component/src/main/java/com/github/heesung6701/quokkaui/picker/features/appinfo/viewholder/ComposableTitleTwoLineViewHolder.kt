package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoTitleTwoLineBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

class ComposableTitleTwoLineViewHolder(itemView: View) :
    ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoTitleTwoLineBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is AppInfoSubTitleViewModel) {
            binding.appName.text = viewModel.appName
            binding.subTitle.text = viewModel.appName
        }
    }
}