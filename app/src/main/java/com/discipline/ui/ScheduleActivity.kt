package com.discipline.ui

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.discipline.R
import com.discipline.data.RoutineManager
import com.discipline.data.Schedule

class ScheduleActivity : AppCompatActivity() {

    private lateinit var routineManager: RoutineManager
    private var startHour = 9
    private var startMinute = 0
    private var endHour = 17
    private var endMinute = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        routineManager = RoutineManager(this)

        setupUI()
    }

    private fun setupUI() {
        val nameInput = findViewById<EditText>(R.id.et_routine_name)
        val startTimeBtn = findViewById<Button>(R.id.btn_start_time)
        val endTimeBtn = findViewById<Button>(R.id.btn_end_time)
        val appsToBlockCheckboxes = findViewById<LinearLayout>(R.id.ll_apps_to_block)
        val saveBtn = findViewById<Button>(R.id.btn_save_routine)

        // Update time display
        startTimeBtn.text = String.format("%02d:%02d", startHour, startMinute)
        endTimeBtn.text = String.format("%02d:%02d", endHour, endMinute)

        startTimeBtn.setOnClickListener {
            TimePickerDialog(
                this,
                { _, h, m ->
                    startHour = h
                    startMinute = m
                    startTimeBtn.text = String.format("%02d:%02d", h, m)
                },
                startHour,
                startMinute,
                true
            ).show()
        }

        endTimeBtn.setOnClickListener {
            TimePickerDialog(
                this,
                { _, h, m ->
                    endHour = h
                    endMinute = m
                    endTimeBtn.text = String.format("%02d:%02d", h, m)
                },
                endHour,
                endMinute,
                true
            ).show()
        }

        // Add app blocking categories
        addAppCategory(appsToBlockCheckboxes, "Social Media", "whatsapp", "instagram", "tiktok", "facebook")
        addAppCategory(appsToBlockCheckboxes, "Gaming", "games", "pubg", "freefire")
        addAppCategory(appsToBlockCheckboxes, "Entertainment", "youtube", "netflix")

        saveBtn.setOnClickListener {
            val name = nameInput.text.toString().trim()
            if (name.isEmpty()) {
                Toast.makeText(this, "Please enter routine name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val schedule = Schedule(
                id = System.currentTimeMillis().toString(),
                name = name,
                startTime = String.format("%02d:%02d", startHour, startMinute),
                endTime = String.format("%02d:%02d", endHour, endMinute),
                daysActive = "Mon,Tue,Wed,Thu,Fri,Sat,Sun"
            )

            routineManager.saveSchedule(schedule)
            Toast.makeText(this, "Routine saved!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun addAppCategory(parent: LinearLayout, categoryName: String, vararg appKeywords: String) {
        val categoryView = LinearLayout(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(0, 16, 0, 0)
            }
            orientation = LinearLayout.VERTICAL
        }

        val categoryTitle = TextView(this).apply {
            text = categoryName
            textSize = 14f
            setTypeface(null, android.graphics.Typeface.BOLD)
            setTextColor(0xFF1976D2.toInt())
        }
        categoryView.addView(categoryTitle)

        val checkBox = CheckBox(this).apply {
            text = "Block all $categoryName apps"
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 8, 0, 0)
            }
        }
        categoryView.addView(checkBox)

        parent.addView(categoryView)
    }
}
