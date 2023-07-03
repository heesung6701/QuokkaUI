package com.github.heesung6701.quokkaui.picker.features.composable

interface ComposableType {
    val leftFrame: ComposableFrame?
    val iconFrame: ComposableFrame?
    val titleFrame: ComposableFrame?
    val widgetFrame: ComposableFrame?

    private data class ComposableTypeImpl(
        override val leftFrame: ComposableFrame?,
        override val iconFrame: ComposableFrame?,
        override val titleFrame: ComposableFrame?,
        override val widgetFrame: ComposableFrame?,
    ) : ComposableType

    companion object {
        fun obtain(
            leftFrame: ComposableFrame?,
            iconFrame: ComposableFrame?,
            titleFrame: ComposableFrame?,
            widgetFrame: ComposableFrame?
        ): ComposableType {

            return ComposableTypeImpl(leftFrame, iconFrame, titleFrame, widgetFrame)
        }
    }
}