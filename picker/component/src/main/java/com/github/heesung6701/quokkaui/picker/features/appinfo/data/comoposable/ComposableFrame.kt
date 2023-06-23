package com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable

import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableItemViewHolder

interface ComposableFrame {
    val layoutResId: Int
    val viewHolderClass: Class<out ComposableItemViewHolder>
}