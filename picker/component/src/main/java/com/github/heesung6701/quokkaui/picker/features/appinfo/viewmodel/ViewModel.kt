package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo

sealed class ViewModel {
    abstract val key: Any
    abstract val onItemClicked: ((AppInfo) -> Unit)?
    override fun equals(other: Any?): Boolean {
        if (other is ViewModel) {
            return key == other.key
        }
        return false
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }
}