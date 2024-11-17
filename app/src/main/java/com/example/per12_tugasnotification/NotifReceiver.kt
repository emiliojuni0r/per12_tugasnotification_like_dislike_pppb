package com.example.per12_tugasnotification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotifReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        val sharedPreferences = context?.getSharedPreferences("Counters", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()

        when (action) {
            "ACTION_LIKE" -> {
                val currentLike = sharedPreferences?.getInt("likeCount", 0) ?: 0
                editor?.putInt("likeCount", currentLike + 1)?.apply()
                Toast.makeText(context, "Liked! Total Likes: ${currentLike + 1}", Toast.LENGTH_SHORT).show()
            }
            "ACTION_DISLIKE" -> {
                val currentDislike = sharedPreferences?.getInt("dislikeCount", 0) ?: 0
                editor?.putInt("dislikeCount", currentDislike + 1)?.apply()
                Toast.makeText(context, "Disliked! Total Dislikes: ${currentDislike + 1}", Toast.LENGTH_SHORT).show()
            }
        }

//        val msg = intent?.getStringExtra("MESSAGE")
//        if (msg != null) {
//            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
//        }
    }
}