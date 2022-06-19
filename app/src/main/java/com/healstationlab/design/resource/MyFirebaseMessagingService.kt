package com.healstationlab.design.resource

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.healstationlab.design.R
import com.healstationlab.design.dto.auth
import com.healstationlab.design.ui.EventDetailActivity
import com.healstationlab.design.ui.InqueryActivity
import com.healstationlab.design.ui.PublicNoticeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyFirebaseMessagingService : FirebaseMessagingService() {

//    private val TAG = "FirebaseService"
    lateinit var intent : Intent

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        val body : HashMap<String, String> = HashMap()
        body["pushToken"] = p0
        body["platform"] = "android"
        pushToken(body)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {


        remoteMessage.notification?.let {

        }

        if(remoteMessage.notification != null) {
            val message = remoteMessage.notification?.body
            val title = remoteMessage.notification?.title

            sendNotification(message, title, remoteMessage)
        }
    }

    @SuppressLint("WrongConstant", "UnspecifiedImmutableFlag")
    private fun sendNotification(message: String?, title : String?, remoteMessage: RemoteMessage) {
        if(title!!.contains("이벤트")){
            intent = Intent(this, EventDetailActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                val data = remoteMessage.data["VALUE"]
                putExtra("id", data!!.toInt())
            }
        } else if(title.contains("답변 등록")){
            intent = Intent(this, InqueryActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        } else if(title.contains("공지사항")){
            intent = Intent(this, PublicNoticeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel("10", "test", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "test"
            notificationChannel.enableLights(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
            notificationManager.createNotificationChannel(notificationChannel)
        }


        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this,"10")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.logo))
                .setContentTitle(title)
                .setShowWhen(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setSound(notificationSound)
                .setFullScreenIntent(pendingIntent, true)
                .setSmallIcon(R.mipmap.ic_launcher)

        val notificationManager: NotificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(10, notificationBuilder.build())
    }

    private fun pushToken(body: HashMap<String, String>){
        RetrofitMansae.server.pushToken(body = body)
                .enqueue(object : Callback<auth>{
                    override fun onFailure(call: Call<auth>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<auth>, response: Response<auth>) {
                        when(response.body()?.responseCode){
                            "SUCCESS" -> {

                            }
                        }
                    }
                })
    }
}
