package com.github.heesung6701.quokkaui.picker.features.composable

import androidx.annotation.Keep

@Keep
interface ComposableFrame {
    val layoutResId: Int
    val viewHolderClass: Class<out ComposableItemViewHolder>
}