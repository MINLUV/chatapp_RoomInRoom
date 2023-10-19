package com.example.chatapp_roominroom

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
//import com.google.firebase.database.core.Context

class UserAdapter(private val context: Context, private val userList: ArrayList<User>):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {  //userViewHolder 을 다시 상속받거 UserViewHolder를 적용하기 위해서 RecyclerView.adapter에 넣는다.

    override fun onCreateViewHolder(parent: ViewGroup, viewGroup: Int): UserViewHolder{  //user_layout을 연결하는 기능 구현
        val view:View = LayoutInflater.from(context).inflate(R.layout.user_layout,parent,false)

        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) { //데이터를 전달받아 user_layout에 보여주는 기능 구현
        val currentUser = userList[position] // 데이터 담기
        holder.nameText.text = currentUser.name // 화면에 데이터 보여주기

        holder.itemView.setOnClickListener{
            val intent = Intent(context, ChatActivity::class.java)

            //넘길 데이터
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uID", currentUser.uid)

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int { //userList의 갯수를 리턴해줌
        return  userList.size
    }



    class UserViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){ // RecyclerView.ViewHolder 상속받은 클래스 View 를 전달받아 user_layout 텍스트뷰 객체를 만들수 있따.
        val nameText: TextView = itemView.findViewById(R.id.name_text)
    }
}