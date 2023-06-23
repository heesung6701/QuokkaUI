package com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable

import androidx.annotation.LayoutRes
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableIconViewHolder
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableItemViewHolder

enum class IconFrame(
    @LayoutRes
    override val layoutResId: Int,
    override val viewHolderClass: Class<out ComposableItemViewHolder>
) : ComposableFrame {
    Icon(R.layout.layout_app_info_icon, ComposableIconViewHolder::class.java);
}