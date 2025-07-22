package com.example.gym.screens.login

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.gym.R

@Composable
fun GetAppVersion() {
    val context = LocalContext.current
    val versionName = remember { getAppVersionName(context) }

    Text(
        text = "${stringResource(id = R.string.versão)} $versionName",
        style = MaterialTheme.typography.bodyMedium,
        fontWeight = FontWeight.W500
    )
}

fun getAppVersionName(context: Context): String {
    return try {
        val packageInfo: PackageInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.packageManager.getPackageInfo(context.packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            context.packageManager.getPackageInfo(context.packageName, 0)
        }
        packageInfo.versionName
    } catch (e: PackageManager.NameNotFoundException) {
        "0.0.0"
        Log.d("getAppVersionName", "getAppVersionName: ${e.message}")
    }.toString()
}