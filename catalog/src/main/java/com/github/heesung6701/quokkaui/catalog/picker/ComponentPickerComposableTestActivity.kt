package com.github.heesung6701.quokkaui.catalog.picker

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.heesung6701.quokkaui.catalog.databinding.ActivityComponentPickerComposableTestBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.helper.AppInfoHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ComponentPickerComposableTestActivity : AppCompatActivity() {

    private lateinit var mutableStateFlow: MutableStateFlow<UiState>

    private val none = DataSet("none")

    private val iconDataSet = DataSet("icon")

    private val titleSingleDataSet = DataSet("single line")
    private val titleTwoLineDateSet = DataSet("two line") { index, appInfo ->
        appInfo.subTitle = "sub tible $index"
    }

    private val widgetSwitchDataSet = DataSet("switch") { _, appInfo ->
        appInfo.activate = true
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityComponentPickerComposableTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mutableStateFlow = MutableStateFlow(UiState())

        fun Spinner.setup(dataSetList: List<DataSet>, reducer: (UiState, DataSet) -> UiState) {
            adapter = ArrayAdapter(
                context,
                android.R.layout.simple_spinner_item,
                dataSetList.map { it.name })

            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(context, dataSetList[position].name, Toast.LENGTH_SHORT).show()
                    mutableStateFlow.tryEmit(
                        reducer(
                            mutableStateFlow.value,
                            dataSetList[position]
                        )
                    )
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
            }
            setSelection(0)
        }

        listOf(
            binding.spinnerIcon to listOf(none, iconDataSet),
            binding.spinnerTitle to listOf(titleSingleDataSet, titleTwoLineDateSet),
            binding.spinnerLeft to listOf(none),
            binding.spinnerWidget to listOf(none, widgetSwitchDataSet),
        ).zip(
            listOf<(UiState, DataSet) -> UiState>(
                { uiState, it -> uiState.copy(icon = it) },
                { uiState, it -> uiState.copy(title = it) },
                { uiState, it -> uiState.copy(left = it) },
                { uiState, it -> uiState.copy(widget = it) },
            )
        ).forEach { (pair, reducer) ->
            val (spinner, dataList) = pair
            spinner.setup(dataList, reducer)
        }

        CoroutineScope(Dispatchers.Main).launch {
            mutableStateFlow.collect { uiState ->

                val appInfoList = AppInfoHelper
                    .getInstalledPackages(this@ComponentPickerComposableTestActivity.packageManager)
                    .mapIndexed { index, appInfo ->
                        listOfNotNull(
                            uiState.left,
                            uiState.title,
                            uiState.icon,
                            uiState.widget,
                        ).fold(appInfo) { acc, item ->
                            item.updater?.invoke(index, acc)
                            acc
                        }
                        appInfo
                    }

                binding.componentPicker.submitList(
                    appInfoList
                )
            }
        }
    }

    data class DataSet(
        val name: String,
        val updater: ((Int, AppInfo) -> Unit)? = null,
    )

    data class UiState(
        val left: DataSet? = null,
        val title: DataSet? = null,
        val icon: DataSet? = null,
        val widget: DataSet? = null,
    )
}