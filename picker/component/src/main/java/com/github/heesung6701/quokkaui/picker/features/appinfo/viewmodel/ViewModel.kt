package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo

interface ViewModel {
    val key: Any
    val onItemClicked: ((AppInfo) -> Unit)?
}