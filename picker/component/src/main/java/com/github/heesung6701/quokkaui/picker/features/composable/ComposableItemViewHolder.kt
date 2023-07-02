package com.github.heesung6701.quokkaui.picker.features.composable

import android.view.View
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

abstract class ComposableItemViewHolder(itemView: View) {

    abstract fun bindData(viewModel: ViewModel)

    abstract fun onViewRecycled()
}