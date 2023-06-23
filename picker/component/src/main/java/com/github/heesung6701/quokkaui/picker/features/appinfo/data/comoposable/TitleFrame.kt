package com.github.heesung6701.quokkaui.picker.features.appinfo.data.comoposable

import androidx.annotation.LayoutRes
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableItemViewHolder
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewholder.ComposableTitleSingleLineViewHolder

enum class TitleFrame(
    @LayoutRes
    override val layoutResId: Int,
    override val viewHolderClass: Class<out ComposableItemViewHolder>
) : ComposableFrame {
    SingleLine(
        R.layout.layout_app_info_title_single_line,
        ComposableTitleSingleLineViewHolder::class.java
    );
}