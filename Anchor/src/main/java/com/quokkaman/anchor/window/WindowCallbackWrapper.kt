package com.quokkaman.anchor.window

import android.view.*
import android.view.accessibility.AccessibilityEvent

open class WindowCallbackWrapper(private val base: Window.Callback) : Window.Callback {
    override fun dispatchKeyEvent(p0: KeyEvent?): Boolean = base.dispatchKeyEvent(p0)

    override fun dispatchKeyShortcutEvent(p0: KeyEvent?): Boolean =
        base.dispatchKeyShortcutEvent(p0)

    override fun dispatchTouchEvent(p0: MotionEvent?): Boolean = base.dispatchTouchEvent(p0)

    override fun dispatchTrackballEvent(p0: MotionEvent?): Boolean = base.dispatchTrackballEvent(p0)

    override fun dispatchGenericMotionEvent(p0: MotionEvent?): Boolean =
        base.dispatchGenericMotionEvent(p0)

    override fun dispatchPopulateAccessibilityEvent(p0: AccessibilityEvent?): Boolean =
        base.dispatchPopulateAccessibilityEvent(p0)

    override fun onCreatePanelView(p0: Int): View? = base.onCreatePanelView(p0)

    override fun onCreatePanelMenu(p0: Int, p1: Menu): Boolean = base.onCreatePanelMenu(p0, p1)

    override fun onPreparePanel(p0: Int, p1: View?, p2: Menu): Boolean =
        base.onPreparePanel(p0, p1, p2)

    override fun onMenuOpened(p0: Int, p1: Menu): Boolean = base.onMenuOpened(p0, p1)

    override fun onMenuItemSelected(p0: Int, p1: MenuItem): Boolean =
        base.onMenuItemSelected(p0, p1)

    override fun onWindowAttributesChanged(p0: WindowManager.LayoutParams?) {
        base.onWindowAttributesChanged(p0)
    }

    override fun onContentChanged() {
        base.onContentChanged()
    }

    override fun onWindowFocusChanged(p0: Boolean) {
        base.onWindowFocusChanged(p0)
    }

    override fun onAttachedToWindow() {
        base.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        base.onDetachedFromWindow()
    }

    override fun onPanelClosed(p0: Int, p1: Menu) {
        base.onPanelClosed(p0, p1)
    }

    override fun onSearchRequested(): Boolean {
        return base.onSearchRequested()
    }

    override fun onSearchRequested(p0: SearchEvent?): Boolean =
        base.onSearchRequested(p0)

    override fun onWindowStartingActionMode(p0: ActionMode.Callback?): ActionMode? =
        base.onWindowStartingActionMode(p0)

    override fun onWindowStartingActionMode(p0: ActionMode.Callback?, p1: Int): ActionMode? =
        base.onWindowStartingActionMode(p0, p1)

    override fun onActionModeStarted(p0: ActionMode?) {
        return base.onActionModeStarted(p0)
    }

    override fun onActionModeFinished(p0: ActionMode?) {
        base.onActionModeFinished(p0)
    }
}