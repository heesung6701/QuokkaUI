package com.github.heesung6701.quokkaui.picker.features.di

import android.content.Context
import com.github.heesung6701.quokkaui.picker.features.appinfo.adapter.AppInfoListAdapter
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.ComposableBitConverter
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.ComposableFactory
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.DefaultFrameStrategy
import com.github.heesung6701.quokkaui.picker.features.appinfo.composable.FrameStrategy
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModelFactory

open class Providers(context: Context) {

    private val frameStrategy: FrameStrategy by lazy { DefaultFrameStrategy() }

    private val frameSetBitConverter: ComposableBitConverter by lazy { ComposableBitConverter(frameStrategy) }

    private val composableFactory: ComposableFactory by lazy { ComposableFactory(frameStrategy, frameSetBitConverter) }

    val viewModelFactory by lazy { ViewModelFactory(context) }

    val appInfoListAdapter by lazy { AppInfoListAdapter(composableFactory) }
}