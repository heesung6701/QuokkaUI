package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

class FrameIndexedArrayTest {

    @Test
    fun test_FrameSet() {
        val mockFrameList = (0..10).map {
            mock(ComposableFrame::class.java)
        }
        val frameIndexedArray = FrameIndexedArray()
        mockFrameList.forEach {
            frameIndexedArray.addFrame(it)
        }
        Assert.assertEquals(mockFrameList.size, frameIndexedArray.getSize())
        mockFrameList.forEachIndexed { index, composableFrame ->
            Assert.assertEquals(index + 1, frameIndexedArray.getFrameId(composableFrame))
        }
    }
}