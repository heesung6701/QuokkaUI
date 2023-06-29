package com.github.heesung6701.quokkaui.picker.features.appinfo.data

data class AppInfo(val packageName: String, val activityName: String) {
    var subTitle: String? = null
    var activate: Boolean? = null
    var onItemClicked: ((AppInfo) -> Unit)? = null
}