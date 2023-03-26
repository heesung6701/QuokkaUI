package com.quokkaman.qukkaui.anchor.window

import android.os.Bundle
import android.view.View
import android.view.ViewGroup.MarginLayoutParams
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.quokkaman.qukkaui.R
import com.quokkaman.qukkaui.common.findAllViews
import com.quokkaman.anchor.window.WindowAnchorManager
import java.util.*

class DialogAnchorActivity : AppCompatActivity() {
    private var anchorManager: WindowAnchorManager? = null
    companion object {
        const val PERIOD = 1000L
    }

    class RandomUpdatePositionTask(private val view: View) : TimerTask() {
        override fun run() {
            val randomX = Math.random() * (view.context.resources.displayMetrics.widthPixels - view.width)
            val randomY = Math.random() * (view.context.resources.displayMetrics.heightPixels - view.height)

            view.post {
                val params = view.layoutParams as MarginLayoutParams
                params.setMargins(randomX.toInt(), randomY.toInt(), 0, 0)
                view.layoutParams = params
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog_anchor)

        val dialog = AlertDialog.Builder(this, R.style.CustomSmallDialog)
            .setTitle("title")
            .setMessage("message")
            .setPositiveButton("OK") { _, _ ->

            }.create()
        anchorManager = WindowAnchorManager(dialog.window!!)

        var timer = Timer()

        val buttons = findAllViews { it is Button }
        buttons.forEach {
            it.setOnClickListener { view ->
                anchorManager?.attach(view)
                dialog.show()

                if (view.id == R.id.btnRandom) {
                    timer.purge()
                    timer = Timer()
                    timer.schedule(RandomUpdatePositionTask(view), PERIOD, PERIOD)
                } else {
                    timer.cancel()
                }
            }
        }
    }
}