package com.github.heesung6701.quokkaui.picker.extend

import android.graphics.drawable.Drawable
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun ImageView.loadIcon(iconFlow: Flow<Drawable>) {
    CoroutineScope(Dispatchers.IO).launch {
        iconFlow.collect {
            withContext(Dispatchers.Main) {
                setImageDrawable(it)
            }
        }
    }
}