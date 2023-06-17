package com.github.heesung6701.quokkaui.picker.appinfo.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.github.heesung6701.quokkaui.picker.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoBinding
import com.github.heesung6701.quokkaui.picker.extend.loadIcon

class AppInfoViewHolder(private val binding: ListItemAppInfoBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(viewModel: AppInfoViewModel) {
        binding.appName.text = viewModel.appName
        binding.appIcon.loadIcon(viewModel.appIcon)
    }
}