package com.github.heesung6701.quokkaui.picker.widget

import android.content.Context
import android.util.AttributeSet
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.appinfo.adapter.AppInfoListAdapter
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.features.appinfo.decorator.RoundItemDecorator
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.HasSwitch
import com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel.ViewModelFactory
import com.github.heesung6701.quokkaui.picker.features.di.Providers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ComponentPickerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.textViewStyle,
) : RecyclerView(context, attrs, defStyleAttr) {

    interface OnActivateChangeListener {
        fun onActivateChanged(appInfo: AppInfo)
    }

    private var onActivateChangeListener: OnActivateChangeListener? = null

    @VisibleForTesting
    val viewModelFactory: ViewModelFactory

    private val appInfoListAdapter: AppInfoListAdapter

    init {
        val providerClassName =
            context.obtainStyledAttributes(attrs, R.styleable.ComponentPickerView).use {
                it.getString(R.styleable.ComponentPickerView_providerClass)
            }

        val providers: Providers = try {
            val clazz: Class<*> = Class.forName(providerClassName!!)
            clazz.getConstructor(Context::class.java).newInstance(context) as Providers
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            Providers(context)
        }
        catch (e: RuntimeException) {
            e.printStackTrace()
            Providers(context)
        }

        viewModelFactory = providers.viewModelFactory
        appInfoListAdapter = providers.appInfoListAdapter

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

    fun setOnActivateChangeListener(listener: OnActivateChangeListener) {
        onActivateChangeListener = listener
    }

    fun setShowAllApps(showAllApps: Boolean) {
        appInfoListAdapter.setShowAllApps(showAllApps)
    }
}