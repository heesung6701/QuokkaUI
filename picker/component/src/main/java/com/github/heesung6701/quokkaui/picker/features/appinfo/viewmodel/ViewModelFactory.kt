package com.github.heesung6701.quokkaui.picker.features.appinfo.viewmodel

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager.NameNotFoundException
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.heesung6701.quokkaui.picker.R
import com.github.heesung6701.quokkaui.picker.features.appinfo.data.AppInfo
import kotlinx.coroutines.flow.flow

class ViewModelFactory(val context: Context) {

    private val packageManager = context.packageManager

    fun createAppInfoViewModel(appInfo: AppInfo): AppInfoViewModel {
        return AppInfoViewModel(appInfo,
            appName = getDefaultLabel(appInfo),
            appIcon = flow {
                emit(getDefaultIcon(appInfo))
            }
        )
    }

    private fun getDefaultLabel(appInfo: AppInfo): String {
        return if (appInfo.activityName.isNotEmpty()) {
            try {
                packageManager.getActivityInfo(
                    ComponentName(appInfo.packageName, appInfo.activityName),
                    0
                ).loadLabel(packageManager).toString()
            } catch (e: NameNotFoundException) {
                "Unknown"
            }
        } else {
            val applicationInfo = packageManager.getApplicationInfo(appInfo.packageName, 0)
            packageManager.getApplicationLabel(applicationInfo).toString()
        }
    }

    private fun getDefaultIcon(appInfo: AppInfo): Drawable {
        return try {
            if (appInfo.activityName.isNotEmpty()) {
                packageManager.getActivityIcon(ComponentName(appInfo.packageName, appInfo.activityName))
            } else {
                packageManager.getApplicationIcon(appInfo.packageName)
            }
        } catch (e: NameNotFoundException) {
            ContextCompat.getDrawable(context, R.drawable.baseline_error_24)!!
        }
    }
}