package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import android.graphics.drawable.Drawable
import kotlinx.coroutines.flow.Flow

interface HasAppIcon {
    val appIcon: Flow<Drawable>
}