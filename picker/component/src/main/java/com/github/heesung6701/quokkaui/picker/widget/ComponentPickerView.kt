package com.github.heesung6701.quokkaui.picker.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.heesung6701.quokkaui.picker.features.appinfo.adapter.AppInfoListAdapter
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.features.appinfo.decorator.RoundItemDecorator
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModelFactory

class ComponentPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
) : RecyclerView(context, attrs, defStyleAttr) {

    private val appInfoListAdapter = AppInfoListAdapter()

    private val viewModelFactory =
        ViewModelFactory(context)

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = appInfoListAdapter
        addItemDecoration(RoundItemDecorator())
    }

    fun submitList(items: List<AppInfo>) {
        appInfoListAdapter.submitList(items.map {
            viewModelFactory.createAppInfoViewModel(it)
        })
    }
}