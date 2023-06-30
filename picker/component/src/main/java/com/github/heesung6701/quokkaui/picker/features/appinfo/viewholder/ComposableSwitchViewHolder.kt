package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoSwitchBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasSwitch
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComposableSwitchViewHolder(itemView: View) : ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoSwitchBinding.bind(itemView)

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is HasSwitch) {
            CoroutineScope(Dispatchers.Main).launch {
                viewModel.activateFlow.collect {
                    binding.switchActivate.isChecked = it
                }
            }
            binding.switchActivate.setOnCheckedChangeListener { _, b ->
                CoroutineScope(Dispatchers.Main).launch {
                    viewModel.activateFlow.emit(b)
                }
            }
        }
    }
}