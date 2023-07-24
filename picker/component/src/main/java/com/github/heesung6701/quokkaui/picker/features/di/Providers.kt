package com.github.heesung6701.quokkaui.picker.features.di

import android.content.Context
import androidx.annotation.Keep
import com.github.heesung6701.quokkaui.picker.features.appinfo.adapter.AppInfoListAdapter
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.ComposableBitConverter
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.ComposableFactory
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.DefaultFrameStrategy
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.FrameStrategy
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModelFactory

@Keep
open class Providers(context: Context) {

    open val frameStrategy: FrameStrategy by lazy { DefaultFrameStrategy() }

    open val frameSetBitConverter: ComposableBitConverter by lazy { ComposableBitConverter(frameStrategy) }

    open val composableFactory: ComposableFactory by lazy { ComposableFactory(frameStrategy, frameSetBitConverter) }

    open val viewModelFactory by lazy { ViewModelFactory(context) }

    open val appInfoListAdapter by lazy { AppInfoListAdapter(composableFactory) }
}