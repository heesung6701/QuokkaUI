package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.IntRange
import com.github.heesung6701.quokkaui.picker.databinding.ListItemAppInfoFrameBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.icon.IconFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.left.LeftFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.title.TitleFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.widget.WidgetFrame
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AllSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSubTitleViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoSwitchViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.AppInfoViewModel
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModel
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableTypeImpl
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableViewHolder

class ComposableFactory {

    companion object {

        val TAG = this::class.simpleName

        private const val BIT_PER_FRAME = 4
        private const val FRAME_COUNT = 4
        private const val TOTAL_BIT = BIT_PER_FRAME * FRAME_COUNT
        private const val MAX_ITEM_TYPE = (1 shl TOTAL_BIT) - 1
        const val MAX_FRAME_ID = (1 shl BIT_PER_FRAME) - 1
        val RANGE = 0..MAX_ITEM_TYPE

        private fun getBitWithRange(data: Int, range: kotlin.ranges.IntRange): Int {
            val bitMask = (1 shl (range.last + 1)) - (1 shl range.first)
            return (data and bitMask) shr range.first
        }

        private val LEFT_BIT_RANGE = (BIT_PER_FRAME * 3) until (BIT_PER_FRAME * 4)
        private val ICON_BIT_RANGE = (BIT_PER_FRAME * 2) until (BIT_PER_FRAME * 3)
        private val TITLE_BIT_RANGE = BIT_PER_FRAME until (BIT_PER_FRAME * 2)
        private val WIDGET_BIT_RANGE = 0 until BIT_PER_FRAME

        @IntRange(from = 0, to = MAX_FRAME_ID.toLong())
        fun getLeftFrameId(data: Int): Int =
            getBitWithRange(data, LEFT_BIT_RANGE)

        @IntRange(from = 0, to = MAX_FRAME_ID.toLong())
        fun getIconFrameId(data: Int): Int =
            getBitWithRange(data, ICON_BIT_RANGE)

        @IntRange(from = 0, to = MAX_FRAME_ID.toLong())
        fun getTitleFrameId(data: Int): Int =
            getBitWithRange(data, TITLE_BIT_RANGE)

        @IntRange(from = 0, to = MAX_FRAME_ID.toLong())
        fun getWidgetFrameId(data: Int): Int =
            getBitWithRange(data, WIDGET_BIT_RANGE)
    }

    @IntRange(from = 0, to = MAX_ITEM_TYPE.toLong())
    annotation class ComposableItemType

    private val leftFrameIndexedArray: FrameIndexedArray = FrameIndexedArray().apply {
        LeftFrame.values().forEach(this::addFrame)
    }

    private val iconFrameIndexedArray: FrameIndexedArray = FrameIndexedArray().apply {
        IconFrame.values().forEach(this::addFrame)
    }

    private val titleFrameIndexedArray: FrameIndexedArray = FrameIndexedArray().apply {
        TitleFrame.values().forEach(this::addFrame)
    }

    private val widgetFrameIndexedArray: FrameIndexedArray = FrameIndexedArray().apply {
        WidgetFrame.values().forEach(this::addFrame)
    }

    fun createViewHolder(
        parent: ViewGroup,
        @ComposableItemType viewType: Int
    ): ComposableViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemAppInfoFrameBinding.inflate(inflater, parent, false)
        val leftFrameId = getLeftFrameId(viewType)
        val iconFrameId = getIconFrameId(viewType)
        val titleFrameId = getTitleFrameId(viewType)
        val widgetFrameId = getWidgetFrameId(viewType)

        val composableType = ComposableTypeImpl(
            leftFrame = leftFrameIndexedArray[leftFrameId],
            iconFrame = iconFrameIndexedArray[iconFrameId],
            titleFrame = titleFrameIndexedArray[titleFrameId],
            widgetFrame = widgetFrameIndexedArray[widgetFrameId],
        )
        return ComposableViewHolder(binding, composableType)
    }

    fun getItemType(composableType: ComposableType): Int {
        return listOf(
            composableType.leftFrame to (LEFT_BIT_RANGE to leftFrameIndexedArray),
            composableType.iconFrame to (ICON_BIT_RANGE to iconFrameIndexedArray),
            composableType.titleFrame to (TITLE_BIT_RANGE to titleFrameIndexedArray),
            composableType.widgetFrame to (WIDGET_BIT_RANGE to widgetFrameIndexedArray),
        ).fold(0) { acc: Int, (frame, pair) ->
            frame?.let {
                val (range, frameSet) = pair
                val frameId = frameSet.getFrameId(it) ?: return@let null
                acc or (frameId shl range.first)
            } ?: acc
        }
    }

    @ComposableItemType
    fun getItemType(viewModel: ViewModel): Int? {
        return when (viewModel) {
            is AllSwitchViewModel -> {
                ComposableTypeSet.AllSwitch
            }

            is AppInfoSwitchViewModel -> {
                ComposableTypeSet.SwitchPreference
            }

            is AppInfoSubTitleViewModel -> {
                ComposableTypeSet.TwoTextLine
            }

            is AppInfoViewModel -> {
                ComposableTypeSet.SingleTextLine
            }

            else -> {
                null
            }
        }?.run {
            return getItemType(this)
        }
    }
}