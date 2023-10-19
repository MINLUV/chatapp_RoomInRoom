package com.example.chatapp_roominroom

data class Message(
    var message: String?,
    var sendId: String?
){
    constructor():this("","")
}
