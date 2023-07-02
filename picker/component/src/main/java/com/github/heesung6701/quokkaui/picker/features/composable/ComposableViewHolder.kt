package com.github.heesung6701.quokkaui.picker.features.composable

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel

class ComposableViewHolder(binding: ListItemAppInfoFrameBinding, composableType: ComposableType) :
    RecyclerView.ViewHolder(binding.root) {

    private val composableItemViewHolderList: List<ComposableItemViewHolder>

    init {
        composableItemViewHolderList = listOf(
            composableType.leftFrame to binding.leftFrame,
            composableType.widgetFrame to binding.widgetFrame,
            composableType.titleFrame to binding.titleFrame,
            composableType.iconFrame to binding.iconFrame,
        ).mapNotNull { (type, viewStub) ->
            type ?: return@mapNotNull null
            type.viewHolderClass
                .getDeclaredConstructor(View::class.java)
                .newInstance(viewStub.run {
                    layoutResource = type.layoutResId
                    viewStub.inflate()
                })
        }
    }

    fun bindData(viewModel: ViewModel) {
        composableItemViewHolderList.forEach {
            it.bindData(viewModel)
        }
    }

    fun onViewRecycled() {
    }
}