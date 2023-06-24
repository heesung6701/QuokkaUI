package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoSwitchBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

class ComposableSwitchViewHolder(itemView: View) : ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoSwitchBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is AppInfoSwitchViewModel) {
            binding.switchActivate.isChecked = viewModel.activate
        }
    }
}