package com.github.heesung6701.quokkaui.anchor.strategy

import android.graphics.Rect
import android.view.WindowManager
import com.github.heesung6701.quokkaui.anchor.window.strategy.WindowAnchorStrategy
import org.junit.Assert
import org.junit.Test

class WindowAnchorStrategyTest {

    companion object {
        const val WINDOW_WIDTH = 100
        const val WINDOW_HEIGHT = 200
        const val ANCHOR_VIEW_WIDTH = 100
        const val ANCHOR_VIEW_HEIGHT = 100
        const val STATUS_BAR_HEIGHT = 63
        const val BOTTOM_BAR_HEIGHT = 63
        const val SCREEN_WIDTH = 1080
        const val SCREEN_HEIGHT = 1920
    }

    class DefaultParameter : WindowAnchorStrategy.Parameters {
        // Window Size: w100 x h200
        override fun getWindowRect(): Rect = Rect(100, 150, 100 + WINDOW_WIDTH, 150 + WINDOW_HEIGHT)

        override fun getAnchorRect(): Rect =
            Rect(500, 500, 500 + ANCHOR_VIEW_WIDTH, 500 + ANCHOR_VIEW_HEIGHT)

        override fun getRootRect(): Rect =
            Rect(0, STATUS_BAR_HEIGHT, SCREEN_WIDTH, SCREEN_HEIGHT - BOTTOM_BAR_HEIGHT)
    }

    open class ParameterWrapper(private val base: WindowAnchorStrategy.Parameters = DefaultParameter()) :
        WindowAnchorStrategy.Parameters {
        override fun getWindowRect(): Rect = base.getWindowRect()

        override fun getAnchorRect(): Rect = base.getAnchorRect()

        override fun getRootRect(): Rect = base.getRootRect()
    }

    open class DeviceScreenParameter(
        base: WindowAnchorStrategy.Parameters,
        val screenWidth: Int,
        val screenHeight: Int
    ) : ParameterWrapper(base) {
        override fun getRootRect(): Rect =
            Rect(0, STATUS_BAR_HEIGHT, screenWidth, screenHeight - BOTTOM_BAR_HEIGHT)
    }

    private class PhoneParameter(base: WindowAnchorStrategy.Parameters = DefaultParameter()) :
        DeviceScreenParameter(base, 1080, 1920) {
    }

    private class FoldParameter(base: WindowAnchorStrategy.Parameters = DefaultParameter()) :
        DeviceScreenParameter(base, 2200, 2480) {
    }

    private class FlipParameter(base: WindowAnchorStrategy.Parameters = DefaultParameter()) :
        DeviceScreenParameter(base, 1080, 2640) {
    }

    private class TabletParameter(base: WindowAnchorStrategy.Parameters = DefaultParameter()) :
        DeviceScreenParameter(base, 1080, 1920) {
    }

    private class LandscapeParameter(base: DeviceScreenParameter) :
        DeviceScreenParameter(base, base.screenHeight, base.screenWidth) {
    }

    private class AnchorParameter(base: WindowAnchorStrategy.Parameters, val x: Int, val y: Int) :
        ParameterWrapper(base) {
        override fun getAnchorRect() =
            Rect(x, y, x + ANCHOR_VIEW_WIDTH, y + ANCHOR_VIEW_HEIGHT)
    }

    @Test
    fun testReducer_AnchorOnEdgeAndOuterLeft() {
        testAllDevice { parameters, _, screenHeight ->
            listOf(
                Pair(-WINDOW_WIDTH, 0),
                Pair(-WINDOW_WIDTH / 2, 0),
                Pair(-ANCHOR_VIEW_WIDTH / 2, 0),
                Pair(0, 0),
            ).flatMap {
                listOf(
                    Pair(it.first, 0),
                    Pair(it.first, screenHeight / 2),
                    Pair(it.first, screenHeight)
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    0,
                    newAttrs.x
                )
            }
        }
    }

    @Test
    fun testReducer_AnchorOnEdgeAndOuterRight() {
        testAllDevice { parameters, screenWidth, screenHeight ->
            listOf(
                Pair(screenWidth, 0),
                Pair(screenWidth - ANCHOR_VIEW_WIDTH / 2, 0),
                Pair(screenWidth - ANCHOR_VIEW_WIDTH, 0),
                Pair(screenWidth - WINDOW_WIDTH / 2, 0),
            ).flatMap {
                listOf(
                    Pair(it.first, 0),
                    Pair(it.first, screenHeight / 2),
                    Pair(it.first, screenHeight)
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    screenWidth - WINDOW_WIDTH,
                    newAttrs.x
                )
            }
        }
    }

    @Test
    fun testReducer_AnchorOnEdgeTop() {
        testAllDevice { parameters, screenWidth, _ ->
            listOf(
                Pair(0, STATUS_BAR_HEIGHT)
            ).flatMap {
                listOf(
                    Pair(0, it.second),
                    Pair(screenWidth / 2, it.second),
                    Pair(screenWidth, it.second),
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    ANCHOR_VIEW_HEIGHT,
                    newAttrs.y
                )
            }
        }
    }

    @Test
    fun testReducer_AnchorOnOuterTop() {
        testAllDevice { parameters, screenWidth, _ ->
            listOf(
                Pair(0, STATUS_BAR_HEIGHT - 1),
                Pair(0, STATUS_BAR_HEIGHT - ANCHOR_VIEW_HEIGHT / 2),
                Pair(0, STATUS_BAR_HEIGHT - ANCHOR_VIEW_HEIGHT),
                Pair(0, STATUS_BAR_HEIGHT - WINDOW_HEIGHT / 2),
                Pair(0, STATUS_BAR_HEIGHT - WINDOW_HEIGHT)
            ).flatMap {
                listOf(
                    Pair(0, it.second),
                    Pair(screenWidth / 2, it.second),
                    Pair(screenWidth, it.second),
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    (anchorParameter.y + ANCHOR_VIEW_HEIGHT)
                        .coerceAtLeast(STATUS_BAR_HEIGHT),
                    newAttrs.y + STATUS_BAR_HEIGHT
                )
            }
        }
    }

    @Test
    fun testReducer_AnchorOnEdgeBottom() {
        testAllDevice { parameters, screenWidth, screenHeight ->
            val screenBottom = screenHeight - BOTTOM_BAR_HEIGHT
            listOf(
                Pair(0, screenBottom - ANCHOR_VIEW_HEIGHT),
            ).flatMap {
                listOf(
                    Pair(0, it.second),
                    Pair(screenWidth / 2, it.second),
                    Pair(screenWidth, it.second),
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    screenBottom - ANCHOR_VIEW_HEIGHT - WINDOW_HEIGHT,
                    newAttrs.y + STATUS_BAR_HEIGHT
                )
            }
        }
    }

    @Test
    fun testReducer_AnchorOnOuterBottom() {
        testAllDevice { parameters, screenWidth, screenHeight ->
            val screenBottom = screenHeight - BOTTOM_BAR_HEIGHT
            listOf(
                Pair(0, screenBottom - ANCHOR_VIEW_HEIGHT + 1),
                Pair(0, screenBottom - ANCHOR_VIEW_HEIGHT / 2),
                Pair(0, screenBottom),
                Pair(0, screenBottom + WINDOW_HEIGHT / 2),
                Pair(0, screenBottom + WINDOW_HEIGHT),
            ).flatMap {
                listOf(
                    Pair(0, it.second),
                    Pair(screenWidth / 2, it.second),
                    Pair(screenWidth, it.second),
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    anchorParameter.y
                        .coerceAtMost(screenBottom) - WINDOW_HEIGHT,
                    newAttrs.y + STATUS_BAR_HEIGHT
                )
            }
        }
    }

    @Test
    fun testReducer_AnchorShowAsBelow() {
        testAllDevice { parameters, screenWidth, screenHeight ->
            val screenBottom = screenHeight - BOTTOM_BAR_HEIGHT
            listOf(
                Pair(0, screenBottom - ANCHOR_VIEW_HEIGHT),
                Pair(0, screenBottom - ANCHOR_VIEW_HEIGHT - WINDOW_HEIGHT / 2),
                Pair(0, screenBottom - ANCHOR_VIEW_HEIGHT - WINDOW_HEIGHT + 1)
            ).flatMap {
                listOf(
                    Pair(0, it.second),
                    Pair(screenWidth / 2, it.second),
                    Pair(screenWidth, it.second),
                )
            }.forEach {
                val anchorParameter =
                    AnchorParameter(parameters, it.first, it.second)
                val prevAttrs = WindowManager.LayoutParams()
                val newAttrs =
                    WindowAnchorStrategy(anchorParameter).apply(prevAttrs)
                Assert.assertEquals(
                    "anchor on (${it.first}, ${it.second}) with device(${parameters.getRootRect()})",
                    anchorParameter.y.coerceAtMost(screenBottom)
                            - WINDOW_HEIGHT,
                    newAttrs.y + STATUS_BAR_HEIGHT
                )
            }
        }
    }

    private fun testAllDevice(onParameter: (params: WindowAnchorStrategy.Parameters, w: Int, h: Int) -> Unit) {
        listOf(
            PhoneParameter(),
            FoldParameter(),
            FlipParameter(),
            TabletParameter(),
        ).flatMap {
            listOf(it, LandscapeParameter(it))
        }.forEach { parameter ->
            val screenWidth = parameter.screenWidth
            val screenHeight = parameter.screenHeight
            onParameter(parameter, screenWidth, screenHeight)
        }
    }
}