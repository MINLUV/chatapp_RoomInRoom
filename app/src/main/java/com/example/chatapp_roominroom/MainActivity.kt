package com.example.chatapp_roominroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatapp_roominroom.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: UserAdapter

    private lateinit var auth: FirebaseAuth
    private lateinit var Dbref: DatabaseReference

    private lateinit var userList : ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        Dbref = Firebase.database.reference

        userList = ArrayList()

        adapter = UserAdapter(this, userList)

        binding.userRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.userRecyclerView.adapter = adapter

        Dbref.child("user").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if(auth.currentUser?.uid != currentUser?.uid){
                        userList.add(currentUser!!)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean{ // 메뉴를 메인 화면에 보여주기 위하여 재정의
        menuInflater.inflate(R.menu.menu, menu)
        return  super.onCreateOptionsMenu(menu)
    }

    // 로그인 르그아웃 기능 구현
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         if(item.itemId == R.id.Log_Out){
             auth.signOut()  //로그아웃
             val intent =Intent(this@MainActivity, LoginAcitvity::class.java) // 로그아웃 후 로그인 화면으로 돌아가는 기능
             startActivity(intent)
             finish()
             return true
         }
        return true
    }
}