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

        val appInfoList = AppInfoHelper.getInstalledPackages(packageManager)
        binding.componentPicker.apply {
            submitList(appInfoList)
            setOnItemClickListener(object: ComponentPickerView.OnItemClickedListener {
                override fun onItemClicked(appInfo: AppInfo) {
                    Toast.makeText(context, appInfo.toString(), Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}