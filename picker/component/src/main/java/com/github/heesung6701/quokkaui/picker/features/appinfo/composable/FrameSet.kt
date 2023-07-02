package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import android.util.Log
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.left.LeftFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame

class FrameSet {
    private var size = 0
    private val frameArray: Array<ComposableFrame?> = Array(ComposableFactory.MAX_FRAME_ID) { null }

    fun addFrame(frame: ComposableFrame): Int? {
        if (size == ComposableFactory.MAX_FRAME_ID) {
            Log.e(
                ComposableFactory.TAG,
                "Left of Composable Frame can only use ${LeftFrame.values().size + 1} ~ ${ComposableFactory.MAX_FRAME_ID}"
            )
            return null
        }
        frameArray[++size] = frame
        return size
    }

    fun getFrameId(frame: ComposableFrame): Int? {
        val index = frameArray.indexOfFirst { it == frame }
        if (index == -1) {
            return addFrame(frame)
        }
        return index
    }

    operator fun get(position: Int): ComposableFrame? {
        return frameArray[position]
    }

    fun getSize(): Int {
        return size
    }
}