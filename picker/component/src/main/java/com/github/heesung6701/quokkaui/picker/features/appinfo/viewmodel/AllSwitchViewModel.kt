package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

data class AllSwitchViewModel(
    override val activateFlow: MutableStateFlow<Boolean>,
) : ViewModel, HasTitle, HasSwitch {
    override val key: String = "AllApps"

    override val title: String
        get() = "All Apps"
}