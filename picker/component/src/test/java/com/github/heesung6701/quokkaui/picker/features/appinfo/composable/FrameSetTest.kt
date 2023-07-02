package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock

class FrameSetTest {

    @Test
    fun test_FrameSet() {
        val mockFrameList = (0..10).map {
            mock(ComposableFrame::class.java)
        }
        val frameSet = FrameSet()
        mockFrameList.forEach {
            frameSet.addFrame(it)
        }
        Assert.assertEquals(mockFrameList.size, frameSet.getSize())
        mockFrameList.forEachIndexed { index, composableFrame ->
            Assert.assertEquals(index + 1, frameSet.getFrameId(composableFrame))
        }
    }
}