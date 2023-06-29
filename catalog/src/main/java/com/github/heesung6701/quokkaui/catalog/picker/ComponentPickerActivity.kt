package com.github.heesung6701.quokkaui.catalog.picker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.heesung6701.quokkaui.catalog.databinding.ActivityComponentPickerBinding
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import com.github.heesung6701.quokkaui.picker.helper.AppInfoHelper
import com.github.heesung6701.quokkaui.picker.widget.ComponentPickerView

class ComponentPickerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityComponentPickerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onItemClickListener: (AppInfo) -> Unit = {
            Toast.makeText(this, it.packageName + "is clicked", Toast.LENGTH_SHORT)
                .show()
        }
        val appInfoList =
            AppInfoHelper.getInstalledPackages(packageManager).mapIndexed { index, appInfo ->
                if (index % 3 == 0) {
                    appInfo.subTitle = "idx - $index"
                }
                if (index % 2 == 0) {
                    appInfo.activate = index % 12 == 0
                }
                if (index % 12 == 0) {
                    appInfo.onItemClicked = onItemClickListener
                }
                appInfo
            }
        binding.componentPicker.apply {
            setShowAllApps(true)
            submitList(appInfoList)
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
    }
}