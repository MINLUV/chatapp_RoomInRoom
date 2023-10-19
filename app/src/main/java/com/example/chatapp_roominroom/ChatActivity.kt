package com.example.chatapp_roominroom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp_roominroom.databinding.ActivityChatBinding
import com.example.chatapp_roominroom.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var receiverName: String
    private lateinit var receiverUid: String

    //바인딩 객체
    private lateinit var binding: ActivityChatBinding

    lateinit var auth: FirebaseAuth
    lateinit var Dbref: DatabaseReference

    private lateinit var receiverRoom: String
    private lateinit var senderRoom: String

    //메시지를 담을리스트
    private lateinit var messageList : ArrayList<Message>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //메시지 초기화
        messageList = ArrayList()
        // 메시지 어뎁터 선언 과 초기화
        val messageAdapter: MessageAdapter = MessageAdapter(this, messageList)

        //리사이클러뷰
        binding.chatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.adapter = messageAdapter

        // 넘어온 데이터 변수에 담기
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
            val message = binding.messageEdit.text.toString()
            val messageObject = Message(message, senderUid)

            //데이터 저장
            Dbref.child("chats").child(senderRoom).child("messages").push().setValue(messageObject).addOnSuccessListener {
                //저장에 성공하면
                Dbref.child("chats").child(receiverRoom).child("messages").push().setValue(messageObject)
            }
            // 입력 부분 초기화
            binding.messageEdit.setText("")
        }

        //메시지 가져오기
        Dbref.child("chats").child(senderRoom).child("messages").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                messageList.clear()

                for (postSnapshot in snapshot.children){
                    val message = postSnapshot.getValue(Message::class.java)
                    messageList.add(message!!)
                }
                messageAdapter.notifyDataSetChanged()  //적용
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}