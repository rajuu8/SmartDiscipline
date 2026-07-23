package com.discipline.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.discipline.data.RoutineManager

class RoutineCheckService : Service() {

    private lateinit var routineManager: RoutineManager

    override fun onCreate() {
        super.onCreate()
        routineManager = RoutineManager(this)
        Log.d("RoutineCheckService", "Service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("RoutineCheckService", "Checking routine status")
        
        val activeSchedule = routineManager.getActiveSchedule()
        if (activeSchedule != null) {
            Log.d("RoutineCheckService", "Active schedule: ${activeSchedule.name}")
        }
        
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
