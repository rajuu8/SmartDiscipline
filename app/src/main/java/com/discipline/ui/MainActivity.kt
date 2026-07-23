package com.discipline.ui

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.discipline.R
import com.discipline.data.RoutineManager
import com.discipline.service.DeviceAdminReceiver

class MainActivity : AppCompatActivity() {

    private lateinit var routineManager: RoutineManager
    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var componentName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        routineManager = RoutineManager(this)
        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        componentName = ComponentName(this, DeviceAdminReceiver::class.java)

        setupUI()
        checkDeviceAdminStatus()
    }

    private fun setupUI() {
        val addScheduleBtn = findViewById<Button>(R.id.btn_add_schedule)
        val viewScheduleBtn = findViewById<Button>(R.id.btn_view_schedule)
        val enableAdminBtn = findViewById<Button>(R.id.btn_enable_admin)
        val statusTextView = findViewById<TextView>(R.id.tv_status)

        addScheduleBtn.setOnClickListener {
            startActivity(Intent(this, ScheduleActivity::class.java))
        }

        viewScheduleBtn.setOnClickListener {
            showSchedules()
        }

        enableAdminBtn.setOnClickListener {
            requestDeviceAdmin()
        }

        updateStatus(statusTextView)
    }

    private fun checkDeviceAdminStatus() {
        val isAdmin = devicePolicyManager.isAdminActive(componentName)
        val statusView = findViewById<TextView>(R.id.tv_admin_status)
        statusView.text = if (isAdmin) "✓ Device Admin: Active" else "✗ Device Admin: Inactive"
        statusView.setTextColor(if (isAdmin) 0xFF00AA00.toInt() else 0xFFAA0000.toInt())
    }

    private fun requestDeviceAdmin() {
        if (!devicePolicyManager.isAdminActive(componentName)) {
            val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName)
            intent.putExtra(
                DevicePolicyManager.EXTRA_ADD_EXPLANATION,
                "Enable Device Admin to lock apps during study time"
            )
            startActivity(intent)
        } else {
            Toast.makeText(this, "Device Admin already enabled", Toast.LENGTH_SHORT).show()
        }
    }

    private fun showSchedules() {
        val schedules = routineManager.getAllSchedules()
        if (schedules.isEmpty()) {
            Toast.makeText(this, "No schedules yet. Add one!", Toast.LENGTH_SHORT).show()
            return
        }

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Your Routines")

        val items = schedules.map { "${it.name}: ${it.startTime} - ${it.endTime}" }.toTypedArray()
        builder.setItems(items) { _, which ->
            val selectedSchedule = schedules[which]
            showScheduleOptions(selectedSchedule)
        }

        builder.setNegativeButton("Close", null)
        builder.show()
    }

    private fun showScheduleOptions(schedule: com.discipline.data.Schedule) {
        val options = arrayOf("Edit", "Delete", "Start Now")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(schedule.name)
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> editSchedule(schedule)
                1 -> deleteSchedule(schedule)
                2 -> startScheduleNow(schedule)
            }
        }
        builder.show()
    }

    private fun editSchedule(schedule: com.discipline.data.Schedule) {
        Toast.makeText(this, "Edit feature coming soon", Toast.LENGTH_SHORT).show()
    }

    private fun deleteSchedule(schedule: com.discipline.data.Schedule) {
        routineManager.deleteSchedule(schedule.id)
        Toast.makeText(this, "Routine deleted", Toast.LENGTH_SHORT).show()
        showSchedules()
    }

    private fun startScheduleNow(schedule: com.discipline.data.Schedule) {
        routineManager.activateSchedule(schedule)
        Toast.makeText(this, "Routine activated! Apps locked.", Toast.LENGTH_LONG).show()
        checkDeviceAdminStatus()
    }

    private fun updateStatus(statusView: TextView) {
        val schedules = routineManager.getAllSchedules()
        val activeSchedule = routineManager.getActiveSchedule()

        statusView.text = when {
            activeSchedule != null -> "Active: ${activeSchedule.name}"
            schedules.isNotEmpty() -> "Ready: ${schedules.size} routines"
            else -> "No routines configured"
        }
    }

    override fun onResume() {
        super.onResume()
        checkDeviceAdminStatus()
        val statusView = findViewById<TextView>(R.id.tv_status)
        updateStatus(statusView)
    }

    private fun AlertDialog.Builder.show(): AlertDialog {
        return this.create().apply { show() }
    }
}
