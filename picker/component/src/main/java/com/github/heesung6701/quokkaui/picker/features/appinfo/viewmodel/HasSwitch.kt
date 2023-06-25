package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow

interface HasSwitch {
    val activateFlow: MutableStateFlow<Boolean>
}