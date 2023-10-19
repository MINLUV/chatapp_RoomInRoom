package com.example.chatapp_roominroom

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatapp_roominroom.databinding.ActivitySignUpactivityBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class SignUpactivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpactivityBinding
    lateinit var auth: FirebaseAuth
    private lateinit var Dbref : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        Dbref = Firebase.database.reference

        //액션바에 상대방 이름 보여주기
        supportActionBar?.title = "회원가입"

        binding.signUpBtn.setOnClickListener{
            val email = binding.emailEdit.text.toString().trim()
            val password = binding.passwordEdit.text.toString().trim()
            val name = binding.nameEdit.text.toString().trim()

            signUp(name,email,password)
        }
    }

    private fun signUp(name: String , email:String , password:String){

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this){task ->
            if(task.isSuccessful){
                Toast.makeText(this, "success sign up",Toast.LENGTH_LONG).show()
                val intent: Intent = Intent(this@SignUpactivity, MainActivity::class.java)
                startActivity(intent)
                addUserToDatabase(name, email, auth.currentUser?.uid!!)
            }
            else{
                Toast.makeText(this,"fail to signup",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uID: String){
        Dbref.child("user").child(uID).setValue(User(name,email,uID))
    }
}