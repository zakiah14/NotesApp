package com.example.notesapp

import java.util.*

class Notes
{
    var id:String ? =null
    var title:String ? = null
    var note:String ? =null
    var timestamp: String?=null

constructor(){}

constructor(id:String,title:String, note:String, timestamp: String)  {
    this.id=id
    this.note=note
    this.title=title
    this.timestamp=timestamp
}

}