package com.github.heesung6701.quokkaui.anchor

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.View.DRAG_FLAG_OPAQUE
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children
import com.github.heesung6701.quokkaui.anchor.test.R
import com.github.heesung6701.quokkaui.anchor.window.WindowAnchorHelper
import java.util.*

/**
 * Adb command for run
 * adb shell am start -n  com.github.heesung6701.quokkaui.anchor.test/com.github.heesung6701.quokkaui.anchor.DynamicButtonTestActivity
 */
class DynamicButtonTestActivity : Activity() {

    lateinit var buttonsList: List<Button>
    var latestDialog: Dialog? = null

    private val windowAnchorHelper = WindowAnchorHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dynamic_button_test_activity)
        buttonsList = findAllViewWithClass(findViewById(R.id.container))

        val container = findViewById<ViewGroup>(R.id.container)

        val button = buttonsList.first().apply {
            setOnClickListener { btn ->
                val dialog = AlertDialog.Builder(
                    this@DynamicButtonTestActivity,
                    R.style.CustomAlertDialog_Small
                )
                    .setTitle("title")
                    .setMessage("This is Custom alert dialog")
                    .setPositiveButton("OK") { _, _ -> }
                    .create()
                windowAnchorHelper.attach(dialog.window!!, btn)
                latestDialog = dialog
                dialog.show()
            }
            setOnLongClickListener {
                startDragAndDrop(
                    null, DragShadowBuilder(
                        this
                    ), container, DRAG_FLAG_OPAQUE
                )
                true
            }
        }
        container.apply {
            setOnDragListener { _, dragEvent ->
                when (dragEvent.action) {
                    DragEvent.ACTION_DROP -> {
                        button.updateCenterPosition(dragEvent.x, dragEvent.y)
                        false
                    }
                    else -> {
                        true
                    }
                }
            }
            post {
                button.updatePosition(
                    width.toFloat() - button.width,
                    height.toFloat() - button.height
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        windowAnchorHelper.clearAll()
    }

    fun View.updatePosition(x: Float, y: Float) {
        this.x = x
        this.y = y
    }

    fun View.updateCenterPosition(x: Float, y: Float) {
        this.x = x - this.width / 2
        this.y = y - this.height / 2
    }

    private inline fun <reified T> findAllViewWithClass(root: View): List<T> {
        val ret = mutableListOf<T>()

        val queue: Queue<View> = LinkedList()
        queue.add(root)
        while (queue.isNotEmpty()) {
            val cur = queue.poll()
            if (cur is ViewGroup) {
                queue.addAll(cur.children)
            }
            if (cur is T) {
                ret.add(cur)
            }
        }
        return ret
    }
}