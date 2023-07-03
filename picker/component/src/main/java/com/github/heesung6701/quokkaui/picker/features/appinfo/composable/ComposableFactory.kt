package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableViewHolder

class ComposableFactory(
    private val frameStrategy: FrameStrategy = DefaultFrameStrategy(),
    private val frameSetBitConverter: ComposableBitConverter = ComposableBitConverter(frameStrategy)
) {

    val range = 0..frameSetBitConverter.maxBit

    fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComposableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoFrameBinding.inflate(inflater, parent, false)
        val composableType = frameSetBitConverter.decodeAsType(viewType)
        return ComposableViewHolder(binding, composableType)
    }

    fun getItemType(composableType: ComposableType): Int {
        return frameSetBitConverter.encodeAsBits(composableType)
    }

    fun getItemType(viewModel: ViewModel): Int {
        return getItemType(frameStrategy.selectComposableType(viewModel))
    }
}