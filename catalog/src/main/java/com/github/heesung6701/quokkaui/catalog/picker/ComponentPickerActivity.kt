package com.github.heesung6701.quokkaui.catalog.picker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.heesung6701.quokkaui.catalog.databinding.ActivityComponentPickerBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.helper.AppInfoHelper
import com.github.heesung6701.quokkaui.picker.widget.ComponentPickerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComponentPickerActivity : AppCompatActivity() {

    private val defaultAppInfoList: DataSet by lazy {
        DataSet(
            name = "기본",
            appInfoList = AppInfoHelper.getInstalledPackages(packageManager),
            configuration = {
                setShowAllApps(false)
            }
        )
    }

    private val subLabelAppInfoList: DataSet by lazy {
        DataSet(
            name = "서브 라벨",
            appInfoList = AppInfoHelper.getInstalledPackages(packageManager)
                .mapIndexed { index, appInfo ->
                    appInfo.subTitle = "index - $index"
                    appInfo
                },
            configuration = {
                setShowAllApps(false)
            }
        )
    }

    private val switchAppInfoList: DataSet by lazy {
        DataSet(
            name = "스위치",
            appInfoList = AppInfoHelper.getInstalledPackages(packageManager)
                .mapIndexed { _, appInfo ->
                    appInfo.activate = false
                    appInfo
                },
            configuration = {
                setShowAllApps(false)
                setOnActivateChangeListener(object : ComponentPickerView.OnActivateChangeListener {
                    override fun onActivateChanged(appInfo: AppInfo) {
                        Toast.makeText(
                            context,
                            "${appInfo.packageName} is changed ${appInfo.activate}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        )
    }

    private val switchAndSubLabelAppInfoList: DataSet by lazy {
        DataSet(
            name = "스위치 + 서브 라벨",
            appInfoList = AppInfoHelper.getInstalledPackages(packageManager)
                .mapIndexed { index, appInfo ->
                    appInfo.activate = false
                    appInfo.subTitle = "index - $index"
                    appInfo
                },
            configuration = {
                setShowAllApps(false)
                setOnActivateChangeListener(object : ComponentPickerView.OnActivateChangeListener {
                    override fun onActivateChanged(appInfo: AppInfo) {
                        Toast.makeText(
                            context,
                            "${appInfo.packageName} is changed ${appInfo.activate}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        )
    }

    private val switchAllAppsAppInfoList: DataSet by lazy {
        DataSet(
            name = "스위치 + All Apps",
            appInfoList = AppInfoHelper.getInstalledPackages(packageManager)
                .mapIndexed { _, appInfo ->
                    appInfo.activate = false
                    appInfo
                },
            configuration = {
                setShowAllApps(true)
                setOnActivateChangeListener(object : ComponentPickerView.OnActivateChangeListener {
                    override fun onActivateChanged(appInfo: AppInfo) {
                        Toast.makeText(
                            context,
                            "${appInfo.packageName} is changed ${appInfo.activate}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        )
    }

    private val switchAndSubLabelWithAllAppsAppInfoList: DataSet by lazy {
        DataSet(
            name = "스위치 + 서브 라벨 + All Apps",
            appInfoList = AppInfoHelper.getInstalledPackages(packageManager)
                .mapIndexed { index, appInfo ->
                    appInfo.activate = false
                    appInfo.subTitle = "index - $index"
                    appInfo
                },
            configuration = {
                setShowAllApps(true)
                setOnActivateChangeListener(object : ComponentPickerView.OnActivateChangeListener {
                    override fun onActivateChanged(appInfo: AppInfo) {
                        Toast.makeText(
                            context,
                            "${appInfo.packageName} is changed ${appInfo.activate}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            }
        )
    }

    private lateinit var mutableStateFlow: MutableStateFlow<UiState>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityComponentPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        listOf(
            defaultAppInfoList,
            subLabelAppInfoList,
            switchAppInfoList,
            switchAndSubLabelAppInfoList,
            switchAllAppsAppInfoList,
            switchAndSubLabelWithAllAppsAppInfoList
        ).apply {
            forEachIndexed { index, dataSet ->
                if (this.lastIndex == index) {
                    return@forEachIndexed
                }
                dataSet.next = this[index + 1]
                this[index + 1].prev = dataSet
            }
        }
        mutableStateFlow = MutableStateFlow(UiState(defaultAppInfoList))

        binding.btnNext.setOnClickListener {
            mutableStateFlow.tryEmit(
                UiState(
                    dataSet = mutableStateFlow.value.dataSet.next!!
                )
            )
        }

        binding.btnPrev.setOnClickListener {
            mutableStateFlow.tryEmit(
                UiState(
                    dataSet = mutableStateFlow.value.dataSet.prev!!
                )
            )
        }

        val onItemClickListener: (AppInfo) -> Unit = {
            Toast.makeText(this, it.packageName + "is clicked", Toast.LENGTH_SHORT).show()
        }

        CoroutineScope(Dispatchers.Main).launch {
            mutableStateFlow.collect { uiState ->
                uiState.dataSet.apply {
                    with(binding.componentPicker, configuration)
                    binding.componentPicker.submitList(
                        appInfoList.map {
                            it.onItemClicked = onItemClickListener
                            it
                        },
                    )
                    binding.tvDataset.text = name
                    binding.btnPrev.visibility = if (prev != null) View.VISIBLE else View.INVISIBLE
                    binding.btnNext.visibility = if (next != null) View.VISIBLE else View.INVISIBLE
                }
            }
        }
    }

    data class UiState(val dataSet: DataSet)

    data class DataSet(
        val name: String,
        val appInfoList: List<AppInfo>,
        val configuration: ComponentPickerView.() -> Unit = {}
    ) {
        var prev: DataSet? = null
        var next: DataSet? = null
    }
}