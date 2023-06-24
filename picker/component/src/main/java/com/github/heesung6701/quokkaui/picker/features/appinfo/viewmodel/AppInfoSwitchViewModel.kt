package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import android.graphics.drawable.Drawable
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import kotlinx.coroutines.flow.Flow

data class AppInfoSwitchViewModel(
    override val key: AppInfo,
    val appName: String,
    override val subTitle: String,
    override val appIcon: Flow<Drawable>,
    override val activate: Boolean,
) : ViewModel, HasTitle, HasSubTitle, HasSwitch, HasAppIcon {
    override val title: String
        get() = appName
}