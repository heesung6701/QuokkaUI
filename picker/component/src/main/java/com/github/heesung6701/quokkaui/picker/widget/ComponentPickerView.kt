package com.github.heesung6701.quokkaui.picker.widget

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.heesung6701.quokkaui.picker.features.appinfo.adapter.AppInfoListAdapter
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.features.appinfo.decorator.RoundItemDecorator
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasSwitch
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComponentPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
) : RecyclerView(context, attrs, defStyleAttr) {

    interface OnItemClickedListener {
        fun onItemClicked(appInfo: AppInfo)
    }

    interface OnActivateChangeListener {
        fun onActivateChanged(appInfo: AppInfo)
    }

    private val appInfoListAdapter = AppInfoListAdapter(onItemClick = {
        onItemClickedListener?.onItemClicked(it)
    })

    private val viewModelFactory =
        ViewModelFactory(context)

    private var onItemClickedListener: OnItemClickedListener? = null
    private var onActivateChangeListener: OnActivateChangeListener? = null

    init {
        layoutManager = LinearLayoutManager(context)
        adapter = appInfoListAdapter
        addItemDecoration(RoundItemDecorator())
    }

    fun submitList(items: List<AppInfo>) {
        appInfoListAdapter.submitList(items.map { appInfo ->
            viewModelFactory.createAppInfoViewModel(appInfo).apply {
                if (this is HasSwitch) {
                    val activateFlow = this.activateFlow
                    CoroutineScope(Dispatchers.Main.immediate).launch {
                        activateFlow.collect {
                            appInfo.activate = it
                            onActivateChangeListener?.onActivateChanged(appInfo)
                        }
                    }
                }
            }
        }.toMutableList())
    }

    fun setOnItemClickListener(listener: OnItemClickedListener) {
        onItemClickedListener = listener
    }
    fun setOnActivateChangeListener(listener: OnActivateChangeListener) {
        onActivateChangeListener = listener
    }

    fun setShowAllApps(showAllApps: Boolean) {
        appInfoListAdapter.setShowAllApps(showAllApps)
    }
}