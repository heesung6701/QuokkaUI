package com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder

import android.view.View
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel

abstract class ComposableItemViewHolder(itemView: View) {

    abstract fun bindData(viewModel: AppInfoViewModel)
}