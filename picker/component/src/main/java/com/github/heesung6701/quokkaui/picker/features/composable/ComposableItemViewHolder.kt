package com.github.heesung6701.quokkaui.picker.features.composable

import android.view.View
import androidx.annotation.Keep
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

@Keep
abstract class ComposableItemViewHolder(itemView: View) {

    abstract fun bindData(viewModel: ViewModel)

    abstract fun onViewRecycled()
}