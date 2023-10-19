package com.example.chatapp_roominroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uID").toString()

        supportActionBar?.title = receiverName
    }
}