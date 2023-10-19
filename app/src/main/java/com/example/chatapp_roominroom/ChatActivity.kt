package com.example.chatapp_roominroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatapp_roominroom.databinding.ActivityChatBinding
import com.example.chatapp_roominroom.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    //바인딩 객체
    private lateinit var binding: ActivityChatBinding

    lateinit var auth: FirebaseAuth
    lateinit var Dbref: DatabaseReference

    private lateinit var receiverRoom: String
    private lateinit var senderRoom: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uID").toString()

        auth = FirebaseAuth.getInstance()
        Dbref = FirebaseDatabase.getInstance().reference

        //접속자 uid
        val senderUid = auth.currentUser?.uid

        //보낸이의 방
        senderRoom = receiverUid + senderUid

        //받는이의 방
        receiverRoom = senderUid + receiverUid

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = receiverName

        //메시지 전송 버튼
        binding.sendBtn.setOnClickListener{
            val message = binding.messageLayout.text.toString()
            val messageObject = Message(message, senderUid)

            //데이터 저장
            Dbref.child("chats").child(senderRoom).child("messages").push().setValue(messageObject).addOnSuccessListener {
                //저장에 성공하면
                Dbref.child("chats").child(receiverRoom).child("messages").push().setValue(messageObject)
            }
        }
    }
}