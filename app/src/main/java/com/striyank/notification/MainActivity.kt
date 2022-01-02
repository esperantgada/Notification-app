package com.striyank.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Build.VERSION_CODES
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.striyank.notification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val CHANNEL_ID = "channel_id_example_01"
    private val notificationId = 10
    private val name = "Notification Title"
    private val descriptionText = "Notification description"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

        binding.textView.setOnClickListener {
            sendNotification()
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= VERSION_CODES.O){
            val importance : Int = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }

            val notificationManager : NotificationManager = getSystemService(Context
                .NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.createNotificationChannel(channel)

        }
    }


    private fun sendNotification(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent : PendingIntent = PendingIntent.getActivity(this, 0,
            intent, 0)

        //Convert a drawable image into bitmap
        val bitmap : Bitmap = BitmapFactory.decodeResource(applicationContext.resources,
            R.drawable.zozor_classe)

        val bitmaLargeIcon : Bitmap = BitmapFactory.decodeResource(applicationContext.resources,
            R.drawable.facebook)



        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Example Title")
            .setContentText("Example description")
            .setLargeIcon(bitmaLargeIcon)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText("This is the first large notification you're receiving from Esperant " +
                        "GADA, a new Android Developer"))
            .setStyle(NotificationCompat.BigPictureStyle().bigPicture(bitmap))
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)){
            notify(notificationId, builder.build())
        }
    }
}