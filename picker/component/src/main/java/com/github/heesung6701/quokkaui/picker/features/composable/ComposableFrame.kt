package com.github.heesung6701.quokkaui.picker.features.composable

interface ComposableFrame {
    val layoutResId: Int
    val viewHolderClass: Class<out ComposableItemViewHolder>
}