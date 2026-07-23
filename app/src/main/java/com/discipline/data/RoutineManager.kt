package com.discipline.data

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoutineManager(private val context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("schedules", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveSchedule(schedule: Schedule) {
        val json = gson.toJson(schedule)
        sharedPreferences.edit().putString("schedule_${schedule.id}", json).apply()
    }

    fun getSchedule(id: String): Schedule? {
        val json = sharedPreferences.getString("schedule_$id", null) ?: return null
        return gson.fromJson(json, Schedule::class.java)
    }

    fun getAllSchedules(): List<Schedule> {
        val allEntries = sharedPreferences.all
        val schedules = mutableListOf<Schedule>()

        allEntries.forEach { (key, value) ->
            if (key.startsWith("schedule_") && value is String) {
                try {
                    val schedule = gson.fromJson(value, Schedule::class.java)
                    schedules.add(schedule)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return schedules.sortedBy { it.name }
    }

    fun deleteSchedule(id: String) {
        sharedPreferences.edit().remove("schedule_$id").apply()
    }

    fun activateSchedule(schedule: Schedule) {
        // Deactivate all others
        getAllSchedules().forEach {
            if (it.id != schedule.id && it.isActive) {
                saveSchedule(it.copy(isActive = false))
            }
        }
        // Activate this one
        saveSchedule(schedule.copy(isActive = true))
    }

    fun getActiveSchedule(): Schedule? {
        return getAllSchedules().find { it.isActive }
    }

    fun deactivateCurrentSchedule() {
        val active = getActiveSchedule()
        if (active != null) {
            saveSchedule(active.copy(isActive = false))
        }
    }

    fun getSchedulesByDay(day: String): List<Schedule> {
        return getAllSchedules().filter { it.daysActive.contains(day) }
    }

    fun isCurrentTimeInSchedule(): Boolean {
        val calendar = java.util.Calendar.getInstance()
        val currentHour = calendar.get(java.util.Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(java.util.Calendar.MINUTE)
        val currentTime = String.format("%02d:%02d", currentHour, currentMinute)

        val active = getActiveSchedule() ?: return false
        return currentTime in active.startTime..active.endTime
    }
}
