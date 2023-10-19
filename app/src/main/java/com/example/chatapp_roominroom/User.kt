package com.example.chatapp_roominroom

data class User(
    var name: String,
    var email : String,
    var uid : String
){
    constructor() : this("","","")
}
