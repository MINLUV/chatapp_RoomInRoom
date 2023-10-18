package com.example.chatapp_roominroom

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatapp_roominroom.databinding.ActivityLoginAcitvityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class LoginAcitvity : AppCompatActivity() {

    //바인딩 객체 생성
    lateinit var binding : ActivityLoginAcitvityBinding
    // 인증 객체
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginAcitvityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 인증 초기화
        auth = FirebaseAuth.getInstance()

        //로그인 버튼 이벤트
        binding.LoginBtn.setOnClickListener {
            val email = binding.Email.text.toString()
            val passwd = binding.Passwd.text.toString()

            login(email,passwd)
        }

    }

    private fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Toast.makeText(
                        this,
                        "login success.",
                        Toast.LENGTH_SHORT,
                    ).show()
                    println(auth.currentUser)
                    val intent: Intent = Intent(this@LoginAcitvity , MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this,
                        "login failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }
    }

}