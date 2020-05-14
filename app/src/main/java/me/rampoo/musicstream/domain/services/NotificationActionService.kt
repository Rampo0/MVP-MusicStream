package me.rampoo.musicstream.domain.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationActionService : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        context!!.sendBroadcast(Intent("NOTIF_ACTION")
            .putExtra("actionname", intent!!.action))
    }
}