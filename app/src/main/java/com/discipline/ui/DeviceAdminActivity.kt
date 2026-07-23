package com.discipline.ui

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.discipline.R
import com.discipline.service.DeviceAdminReceiver

class DeviceAdminActivity : AppCompatActivity() {

    private lateinit var devicePolicyManager: DevicePolicyManager
    private lateinit var componentName: ComponentName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_admin)

        devicePolicyManager = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        componentName = ComponentName(this, DeviceAdminReceiver::class.java)

        setupUI()
    }

    private fun setupUI() {
        val statusTextView = findViewById<TextView>(R.id.tv_device_admin_status)
        val enableButton = findViewById<Button>(R.id.btn_enable_device_admin)
        val disableButton = findViewById<Button>(R.id.btn_disable_device_admin)

        updateStatus(statusTextView)

        enableButton.setOnClickListener {
            if (!devicePolicyManager.isAdminActive(componentName)) {
                Toast.makeText(this, "Enabling Device Admin...", Toast.LENGTH_SHORT).show()
                // The actual permission request is handled in MainActivity
            } else {
                Toast.makeText(this, "Already enabled", Toast.LENGTH_SHORT).show()
            }
        }

        disableButton.setOnClickListener {
            if (devicePolicyManager.isAdminActive(componentName)) {
                devicePolicyManager.removeActiveAdmin(componentName)
                Toast.makeText(this, "Device Admin disabled", Toast.LENGTH_SHORT).show()
                updateStatus(statusTextView)
            }
        }
    }

    private fun updateStatus(statusTextView: TextView) {
        val isActive = devicePolicyManager.isAdminActive(componentName)
        statusTextView.text = if (isActive) {
            "✓ Device Admin: ACTIVE\nYour phone can now enforce routine restrictions."
        } else {
            "✗ Device Admin: INACTIVE\nEnable to enforce app blocking."
        }
        statusTextView.setTextColor(if (isActive) 0xFF00AA00.toInt() else 0xFFAA0000.toInt())
    }

    override fun onResume() {
        super.onResume()
        val statusTextView = findViewById<TextView>(R.id.tv_device_admin_status)
        updateStatus(statusTextView)
    }
}
