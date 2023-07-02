package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class FrameBitConverterTest {

    @Test
    fun test_readBit_P01_there_are_same_types_per_each_frame() {
        val frameStrategy = mock(FrameStrategy::class.java)
        `when`(frameStrategy.leftFrameList).thenReturn((1..15).map { mock(ComposableFrame::class.java) })
        `when`(frameStrategy.titleFrameList).thenReturn((1..15).map { mock(ComposableFrame::class.java) })
        `when`(frameStrategy.iconFrameList).thenReturn((1..15).map { mock(ComposableFrame::class.java) })
        `when`(frameStrategy.widgetFrameList).thenReturn((1..15).map { mock(ComposableFrame::class.java) })

        val converter = FrameBitConverter(frameStrategy)
        Assert.assertEquals((1 shl 16) - 1, converter.maxBit)

        listOf(4, 3, 2, 1).mapIndexed { index, it ->
            val actual = converter.readBit(index, 0x1234)
            Assert.assertEquals(it.toString(2), actual.toString(2))
        }
    }

    @Test
    fun test_readBit_P01_there_are_different_types_per_each_frame() {
        val frameStrategy = mock(FrameStrategy::class.java)
        `when`(frameStrategy.leftFrameList).thenReturn((1..3).map { mock(ComposableFrame::class.java) }) // 2-bit
        `when`(frameStrategy.iconFrameList).thenReturn((1..7).map { mock(ComposableFrame::class.java) }) // 3-bit
        `when`(frameStrategy.titleFrameList).thenReturn((1..15).map { mock(ComposableFrame::class.java) }) // 4-bit
        `when`(frameStrategy.widgetFrameList).thenReturn((1..32).map { mock(ComposableFrame::class.java) }) // 6-bit

        val converter = FrameBitConverter(frameStrategy)
        Assert.assertEquals((1 shl 15) - 1, converter.maxBit)

        listOf(3, 7, 15, 63).mapIndexed { index, it ->
            val actual = converter.readBit(index, 0xffff)
            Assert.assertEquals(it.toString(2), actual.toString(2))
        }
    }

    @Test
    fun test_encodeAsBits_P01() {
        val frameStrategy = mock(FrameStrategy::class.java)
        val leftList = (1..15).map { mock(ComposableFrame::class.java) }
        val iconList = (1..15).map { mock(ComposableFrame::class.java) }
        val titleList = (1..15).map { mock(ComposableFrame::class.java) }
        val widgetList = (1..15).map { mock(ComposableFrame::class.java) }
        `when`(frameStrategy.leftFrameList).thenReturn(leftList)
        `when`(frameStrategy.iconFrameList).thenReturn(iconList)
        `when`(frameStrategy.titleFrameList).thenReturn(titleList)
        `when`(frameStrategy.widgetFrameList).thenReturn(widgetList)

        val frameBitConverter = FrameBitConverter(frameStrategy)
        leftList.forEachIndexed { index, frame ->
            Assert.assertEquals(
                ((index + 1) shl FrameBitConverter.LEFT.times(4)).toString(2),
                frameBitConverter.encodeAsBits(FrameBitConverter.LEFT, frame).toString(2)
            )
        }
        iconList.forEachIndexed { index, frame ->
            Assert.assertEquals(
                ((index + 1) shl FrameBitConverter.ICON.times(4)).toString(2),
                frameBitConverter.encodeAsBits(FrameBitConverter.ICON, frame).toString(2)
            )
        }
        titleList.forEachIndexed { index, frame ->
            Assert.assertEquals(
                ((index + 1) shl FrameBitConverter.TITLE.times(4)).toString(2),
                frameBitConverter.encodeAsBits(FrameBitConverter.TITLE, frame).toString(2)
            )
        }
        widgetList.forEachIndexed { index, frame ->
            Assert.assertEquals(
                ((index + 1) shl FrameBitConverter.WIDGET.times(4)).toString(2),
                frameBitConverter.encodeAsBits(FrameBitConverter.WIDGET, frame).toString(2)
            )
        }
    }
}