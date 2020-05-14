package me.rampoo.musicstream.domain.model

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import me.rampoo.musicstream.R
import me.rampoo.musicstream.data.model.Music
import me.rampoo.musicstream.domain.services.NotificationActionService

object CreateNotification {
    val CHANNEL_ID = "channel1"
    val ACTION_PLAY = "actionplay"

    lateinit var notification : Notification

    fun createNotification(context: Context , music : Music , playButton : Int){

        val notificationManagerCompat = NotificationManagerCompat.from(context)
        val mediaSessionCompat = MediaSessionCompat(context , "tag")

        val icon = BitmapFactory.decodeResource(context.resources , R.drawable.default_image2 )

        val intentPlay = Intent(context , NotificationActionService::class.java).setAction(
            ACTION_PLAY)
        val pendingIntentPlay = PendingIntent.getBroadcast(context , 0
        , intentPlay , PendingIntent.FLAG_UPDATE_CURRENT)

        notification = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.music_style)
            .setContentTitle(music.name)
            .setContentText(music.artist)
            .setLargeIcon(icon)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .addAction(playButton , "Play" , pendingIntentPlay)
            .setStyle(androidx.media.app.NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(0)
                .setMediaSession(mediaSessionCompat.sessionToken))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .build()

        notificationManagerCompat.notify(1, notification)

    }

}