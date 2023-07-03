package com.github.heesung6701.quokkaui.picker.features.appinfo.composable

import com.github.heesung6701.quokkaui.picker.features.composable.ComposableFrame
import com.github.heesung6701.quokkaui.picker.features.composable.ComposableType
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

        val startLeftBit = 0
        val startIconBit = 4
        val startTitleBit = 8
        val startWidgetBit = 12

        val frameBitConverter = FrameBitConverter(frameStrategy)

        val testList = mutableListOf<Pair<Int, ComposableType>>()
        (listOf(null) + leftList).forEachIndexed { i, leftFrame ->
            (listOf(null) + iconList).forEachIndexed { j, iconFrame ->
                (listOf(null) + titleList).forEachIndexed { k, titleFrame ->
                    (listOf(null) + widgetList).forEachIndexed { l, widgetFrame ->
                        if (i + j + k + l == 0) {
                            return
                        }
                        val composableType = ComposableType.obtain(
                            leftFrame = leftFrame,
                            iconFrame = iconFrame,
                            titleFrame = titleFrame,
                            widgetFrame = widgetFrame
                        )
                        val bit = i.shl(startLeftBit) +
                                j.shl(startIconBit) +
                                k.shl(startTitleBit) +
                                l.shl(startWidgetBit)
                        testList.add(bit to composableType)
                    }
                }
            }
        }

        testList.forEach { (bit, type) ->
            Assert.assertEquals(bit.toString(2), frameBitConverter.encodeAsBits(type).toString(2))
            Assert.assertEquals(type, frameBitConverter.decodeAsType(bit))
        }
    }
}