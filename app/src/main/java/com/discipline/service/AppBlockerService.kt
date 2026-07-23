package com.discipline.service

import android.app.ActivityManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import com.discipline.data.RoutineManager

class AppBlockerService : Service() {

    private lateinit var routineManager: RoutineManager
    private val handler = Handler(Looper.getMainLooper())
    private val checkInterval = 1000L // Check every 1 second

    private val blockedAppPackages = listOf(
        "com.whatsapp",
        "com.whatsapp.w4b",
        "com.instagram.android",
        "com.facebook.katana",
        "com.tiktok",
        "com.google.android.youtube",
        "com.netflix.mediaclient",
        "com.tencent.ig",
        "com.pubg.imobile"
    )

    private val runnable = object : Runnable {
        override fun run() {
            checkAndBlockApps()
            handler.postDelayed(this, checkInterval)
        }
    }

    override fun onCreate() {
        super.onCreate()
        routineManager = RoutineManager(this)
        Log.d("AppBlockerService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("AppBlockerService", "Service started")
        handler.post(runnable)
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        Log.d("AppBlockerService", "Service destroyed")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun checkAndBlockApps() {
        if (!routineManager.isCurrentTimeInSchedule()) {
            return
        }

        val activeSchedule = routineManager.getActiveSchedule() ?: return
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager

        try {
            val appsList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activityManager.appTasks.mapNotNull { it.taskInfo?.baseIntent?.component?.packageName }
            } else {
                emptyList()
            }

            appsList.forEach { packageName ->
                if (blockedAppPackages.contains(packageName)) {
                    Log.d("AppBlockerService", "Blocking app: $packageName")
                    bringHomeScreen()
                }
            }
        } catch (e: Exception) {
            Log.e("AppBlockerService", "Error checking apps", e)
        }
    }

    private fun bringHomeScreen() {
        val homeIntent = Intent(Intent.ACTION_MAIN).apply {
            addCategory(Intent.CATEGORY_HOME)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        startActivity(homeIntent)
    }
}
