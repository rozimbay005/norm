package com.example.smsforwarder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return

        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val token = prefs.getString("bot_token", null)
        val chatId = prefs.getString("chat_id", null)

        if (token.isNullOrEmpty() || chatId.isNullOrEmpty()) return

        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        val sender = messages.firstOrNull()?.originatingAddress ?: "Noma'lum"
        val body = messages.joinToString(separator = "") { it.messageBody ?: "" }

        val text = "Yangi SMS\nKimdan: $sender\n\n$body"

        Thread {
            sendToTelegram(token, chatId, text)
        }.start()
    }

    private fun sendToTelegram(token: String, chatId: String, text: String) {
        try {
            val url = URL("https://api.telegram.org/bot$token/sendMessage")
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")

            val params = "chat_id=" + URLEncoder.encode(chatId, "UTF-8") +
                    "&text=" + URLEncoder.encode(text, "UTF-8")

            OutputStreamWriter(connection.outputStream).use { it.write(params) }

            connection.responseCode // triggers the request
            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
