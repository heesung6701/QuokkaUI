package com.github.heesung6701.quokkaui.picker.features.appinfo.composable.widget

import android.view.View
import com.github.heesung6701.quokkaui.picker.databinding.LayoutAppInfoSwitchBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasSwitch
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableItemViewHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComposableSwitchViewHolder(itemView: View) : ComposableItemViewHolder(itemView) {

    private val binding = LayoutAppInfoSwitchBinding.bind(itemView)
    private var job: Job? = null

    override fun bindData(viewModel: ViewModel) {
        if (viewModel is HasSwitch) {
            job?.cancel()
            job = CoroutineScope(Dispatchers.Main).launch {
                viewModel.activateFlow.collect {
                    binding.switchActivate.isChecked = it
                }
            }
            binding.switchActivate.setOnCheckedChangeListener { _, b ->
                viewModel.activateFlow.tryEmit(b)
            }
        }
    }

    override fun onViewRecycled() {
        binding.switchActivate.setOnClickListener(null)
        job?.cancel()
    }
}
