package com.example.smsforwarder

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val permissions = arrayOf(
        Manifest.permission.RECEIVE_SMS,
        Manifest.permission.READ_SMS
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tokenInput = findViewById<EditText>(R.id.tokenInput)
        val chatIdInput = findViewById<EditText>(R.id.chatIdInput)
        val statusText = findViewById<TextView>(R.id.statusText)
        val saveButton = findViewById<Button>(R.id.saveButton)

        val prefs = getSharedPreferences("settings", MODE_PRIVATE)
        tokenInput.setText(prefs.getString("bot_token", ""))
        chatIdInput.setText(prefs.getString("chat_id", ""))

        updateStatus(statusText)

        saveButton.setOnClickListener {
            val token = tokenInput.text.toString().trim()
            val chatId = chatIdInput.text.toString().trim()

            if (token.isEmpty() || chatId.isEmpty()) {
                Toast.makeText(this, "Bot token va chat ID kiriting", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            prefs.edit()
                .putString("bot_token", token)
                .putString("chat_id", chatId)
                .apply()

            Toast.makeText(this, "Saqlandi", Toast.LENGTH_SHORT).show()
            requestPermissionsIfNeeded()
            updateStatus(statusText)
        }

        requestPermissionsIfNeeded()
    }

    private fun requestPermissionsIfNeeded() {
        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        if (notGranted.isNotEmpty()) {
            ActivityCompat.requestPermissions(this, notGranted.toTypedArray(), 100)
        }
    }

    private fun updateStatus(statusText: TextView) {
        val granted = permissions.all {
            ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
        }
        statusText.text = if (granted) {
            "Holat: SMS ruxsati berilgan. Ilova ishga tayyor."
        } else {
            "Holat: SMS ruxsati berilmagan."
        }
    }
}
