package com.github.heesung6701.quokkaui.picker.features.appinfo.decorator

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.github.heesung6701.quokkaui.picker.R

class RoundItemDecorator: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        val adapter = parent.adapter?:return
        val viewHolder= parent.getChildViewHolder(view)
        val position = viewHolder.bindingAdapterPosition
        if (position == RecyclerView.NO_POSITION) {
            return
        }
        viewHolder.itemView.setBackgroundResource(
            when (position) {
                0 -> {
                    R.drawable.drawable_list_top
                }
                adapter.itemCount - 1 -> {
                    R.drawable.drawable_list_bottom
                }
                else -> {
                    R.drawable.drawable_list_middle
                }
            }
        )
    }
}