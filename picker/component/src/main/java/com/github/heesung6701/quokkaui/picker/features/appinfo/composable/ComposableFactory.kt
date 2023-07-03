package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.view.LayoutInflater
import android.view.ViewGroup
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.FrameBitConverter.Companion.ICON
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.FrameBitConverter.Companion.LEFT
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.FrameBitConverter.Companion.TITLE
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.FrameBitConverter.Companion.WIDGET
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableViewHolder

class ComposableFactory(
    private val frameStrategy: FrameStrategy = DefaultFrameStrategy(),
    private val frameSetBitConverter: FrameBitConverter = FrameBitConverter(frameStrategy)
) {

    val range = 0..frameSetBitConverter.maxBit

    fun createViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComposableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoFrameBinding.inflate(inflater, parent, false)
        val leftFrame = frameSetBitConverter.decodeAsFrame(LEFT, viewType)
        val iconFrame = frameSetBitConverter.decodeAsFrame(ICON, viewType)
        val titleFrame = frameSetBitConverter.decodeAsFrame(TITLE, viewType)
        val widgetFrame = frameSetBitConverter.decodeAsFrame(WIDGET, viewType)

        val composableType = ComposableTypeImpl(
            leftFrame = leftFrame,
            iconFrame = iconFrame,
            titleFrame = titleFrame,
            widgetFrame = widgetFrame,
        )
        return ComposableViewHolder(binding, composableType)
    }

    fun getItemType(composableType: ComposableType): Int {
        return listOf(
            composableType.leftFrame to LEFT,
            composableType.iconFrame to ICON,
            composableType.titleFrame to TITLE,
            composableType.widgetFrame to WIDGET,
        )
            .fold(0) { acc: Int, (frame, index) ->
                frame?.let {
                    acc or frameSetBitConverter.encodeAsBits(index, it)
                } ?: acc
            }
    }

    fun getItemType(viewModel: ViewModel): Int {
        return getItemType(frameStrategy.selectComposableType(viewModel))
    }
}