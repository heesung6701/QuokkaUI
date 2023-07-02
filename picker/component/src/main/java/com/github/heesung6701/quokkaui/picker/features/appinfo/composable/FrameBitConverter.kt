package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import androidx.annotation.VisibleForTesting
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import kotlin.math.ceil
import kotlin.math.log2

class FrameBitConverter(
    private val frameStrategy: FrameStrategy = DefaultFrameStrategy()
) {

    companion object {
        private fun getBitWithRange(data: Int, range: IntRange): Int {
            val bitMask = (1 shl (range.last + 1)) - (1 shl range.first)
            return (data and bitMask) shr range.first
        }

        const val OFFSET_FOR_ZERO_AS_NULL = 1
        const val BIT_NULL = 0

        const val LEFT = 0
        const val ICON = 1
        const val TITLE = 2
        const val WIDGET = 3
    }

    private val frameInfo: Array<List<ComposableFrame>> = Array(4) {
        when (it) {
            LEFT -> frameStrategy.leftFrameList
            ICON -> frameStrategy.iconFrameList
            TITLE -> frameStrategy.titleFrameList
            WIDGET -> frameStrategy.widgetFrameList
            else -> throw RuntimeException("UnReachable")
        }
    }

    private val rangeList: Array<IntRange>
    val maxBit: Int

    init {
        var startIndex = 0
        rangeList = frameInfo.map {
            val typeSize = it.size.toDouble() + OFFSET_FOR_ZERO_AS_NULL
            val requiredBits = ceil(log2(typeSize)).toInt()
            val endIndex = startIndex + requiredBits
            val range = startIndex until endIndex
            startIndex = endIndex
            range
        }.toTypedArray()
        maxBit = (1 shl startIndex) - 1
    }

    @VisibleForTesting
    fun readBit(position: Int, data: Int): Int =
        getBitWithRange(data, rangeList[position])

    fun decodeAsFrame(position: Int, data: Int): ComposableFrame? {
        val bitValue = readBit(position, data)
        if (bitValue == BIT_NULL) {
            return null
        }
        return frameInfo[position][bitValue - 1]
    }

    fun encodeAsBits(position: Int, frame: ComposableFrame): Int {
        val data = frameInfo[position].indexOf(frame) + 1
        if (data == 0) {
            return 0
        }
        return data shl rangeList[position].first
    }
}