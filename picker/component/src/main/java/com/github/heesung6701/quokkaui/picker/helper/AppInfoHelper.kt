package com.github.heesung6701.quokkaui.picker.helper

import android.content.Intent
import android.content.pm.PackageManager
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo

object AppInfoHelper {

    @JvmStatic
    fun getInstalledPackages(pm: PackageManager): List<AppInfo> {
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        val result = pm.queryIntentActivities(intent, 0)
        return result.map {
            AppInfo(it.activityInfo.packageName, it.activityInfo?.name?:"")
        }
    }
}