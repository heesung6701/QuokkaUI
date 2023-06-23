package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import android.graphics.drawable.Drawable
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import kotlinx.coroutines.flow.Flow

data class AppInfoSubTitleViewModel(
    override val key: AppInfo,
    val appName: String,
    val appIcon: Flow<Drawable>,
    val subTitle: String
) : ViewModel