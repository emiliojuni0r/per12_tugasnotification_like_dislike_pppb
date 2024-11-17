package com.example.per12_tugasnotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.per12_tugasnotification.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    //Untuk value dari channelId & notifId bisa diisi sesuka hati
    //Asalkan value channelId menggunakan string dan notifId menggunakan integer
    private val channelId = "TEST_NOTIF"
    private val notifId = 90
    private var likeCount = 0
    private var dislikeCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ambil nilai awal likeCount dan dislikeCount dari SharedPreferences
        val sharedPreferences = getSharedPreferences("Counters", Context.MODE_PRIVATE)
        likeCount = sharedPreferences.getInt("likeCount", 0)
        dislikeCount = sharedPreferences.getInt("dislikeCount", 0)

        // Update tampilan layout xml dengan nilai yang diambil
        binding.likeCount.text = likeCount.toString()
        binding.dislikeCount.text = dislikeCount.toString()

        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        binding.btnSendNotification.setOnClickListener {
            val notifImage = BitmapFactory.decodeResource(resources, R.drawable.carti)

            // Intent untuk action Like
            val likeIntent = Intent(this, NotifReceiver::class.java).apply {
                action = "ACTION_LIKE"
            }
            val likePendingIntent = PendingIntent.getBroadcast(
                this, 0, likeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            // Intent untuk action Dislike
            val dislikeIntent = Intent(this, NotifReceiver::class.java).apply {
                action = "ACTION_DISLIKE"
            }
            val dislikePendingIntent = PendingIntent.getBroadcast(
                this, 1, dislikeIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            val builder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.fire)
                .setContentTitle("Notifku")
                .setContentText("apakah gambar ini keren fwaehhh 🧛🦇")
                .setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(notifImage)
                )
                .addAction(R.drawable.like, "Like", likePendingIntent)
                .addAction(R.drawable.dislike, "Dislike", dislikePendingIntent)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            notifManager.notify(notifId, builder.build())
        }

        // Update tampilan berdasarkan perubahan yang ada
        updateCounters()

    }

    override fun onResume() {
        super.onResume()
        // Update counters setiap kali activity di-resume
        updateCounters()
    }

    private fun updateCounters() {
        // Mengambil data dari SharedPreferences
        val sharedPreferences = getSharedPreferences("Counters", Context.MODE_PRIVATE)
        likeCount = sharedPreferences.getInt("likeCount", 0)
        dislikeCount = sharedPreferences.getInt("dislikeCount", 0)

        // Menampilkan data yang terbaru ke layout xml
        binding.likeCount.text = likeCount.toString()
        binding.dislikeCount.text = dislikeCount.toString()
    }
}
