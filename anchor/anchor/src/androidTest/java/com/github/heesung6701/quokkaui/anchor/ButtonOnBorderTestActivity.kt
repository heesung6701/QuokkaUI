package com.github.heesung6701.quokkaui.anchor

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.view.children

import com.github.heesung6701.quokkaui.anchor.test.R
import com.github.heesung6701.quokkaui.anchor.window.WindowAnchorHelper
import java.util.*

/**
 * Adb command for run
 * adb shell am start -n  com.github.heesung6701.quokkaui.anchor.test/com.github.heesung6701.quokkaui.anchor.ButtonOnBorderTestActivity
 */
class ButtonOnBorderTestActivity : Activity() {

    lateinit var buttonsList: List<Button>
    var latestDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.button_on_border_test_activity)
        buttonsList = findAllViewWithClass(findViewById(R.id.container))

        buttonsList.forEach { btn ->
            btn.setOnClickListener {
                val dialog = AlertDialog.Builder(this, R.style.CustomAlertDialog_Small)
                    .setTitle("title")
                    .setMessage("Thie is Custom alert dialog")
                    .setPositiveButton("OK") { _, _ -> }
                    .create()
                WindowAnchorHelper(dialog.window!!)
                    .attach(btn)
                latestDialog = dialog
                dialog.show()
            }
        }
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