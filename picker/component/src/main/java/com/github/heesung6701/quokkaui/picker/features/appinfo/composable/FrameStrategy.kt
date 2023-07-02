package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType

interface FrameStrategy {
    val leftFrameList: List<ComposableFrame>
    val iconFrameList: List<ComposableFrame>
    val titleFrameList: List<ComposableFrame>
    val widgetFrameList: List<ComposableFrame>

    fun selectComposableType(viewModel: ViewModel): ComposableType
}