package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import android.graphics.drawable.Drawable
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import kotlinx.coroutines.flow.Flow

data class AppInfoSubTitleViewModel(
    override val key: AppInfo,
    override val onItemClicked : ((AppInfo) -> Unit)?,
    val appName: String,
    override val appIcon: Flow<Drawable>,
    override val subTitle: String
) : ViewModel(), HasTitle, HasSubTitle, HasAppIcon {
    override val title: String
        get() = appName
}