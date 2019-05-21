package com.example.userlist

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat

class AppNotifications {
   companion object {
       var notificationCounter = 0
   }

   private val CHANNEL_NAME = "UserListChannel"
   private val CHANNEL_ID = 7777

   private fun createNotificationChannel(context: Context) {
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
         val channel = NotificationChannel(
            CHANNEL_ID.toString(), CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
         ).apply {
            description = "UserList channel"
         }
         val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as
                 NotificationManager
         notificationManager.createNotificationChannel(channel)
      }
   }

   fun makeNotifications(context: Context, icon: Int, contentTitle: String, contentText: String, intent: Intent, notificationId: Int){
      var contentIntent = PendingIntent.getActivity(context,
         0, intent,
         PendingIntent.FLAG_CANCEL_CURRENT)
      if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
         createNotificationChannel(context)
         var builder = NotificationCompat.Builder(context, CHANNEL_ID.toString())
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(icon)
            .setLargeIcon(
               BitmapFactory.decodeResource(context.resources,
               icon))
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)
         with(NotificationManagerCompat.from(context)) { notify(notificationId, builder.build()) }
      } else {
         var builder = Notification.Builder(context)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setSmallIcon(icon)
            .setLargeIcon(
               BitmapFactory.decodeResource(context.resources,
                  icon))
            .setAutoCancel(true)
            .setContentIntent(contentIntent)
         val notification = builder.build()
         val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
         manager.notify(CHANNEL_ID, notification)
      }
   }
}