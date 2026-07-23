package com.discipline.data

data class Schedule(
    val id: String,
    val name: String,
    val startTime: String,  // "HH:mm"
    val endTime: String,    // "HH:mm"
    val daysActive: String, // "Mon,Tue,Wed,Thu,Fri,Sat,Sun"
    val blockedApps: List<String> = emptyList(),
    val isActive: Boolean = false
)
