package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class viewNote : AppCompatActivity() {
    var text_title :TextView ?=null
    var text_note :TextView ?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_note)
        conectedviewes()
       var title = intent.extras?.getString("title")
        var note =intent.getStringExtra("note")
        text_title?.text=title
        text_note?.text=note



    }
    private  fun conectedviewes(){
        text_title=findViewById(R.id.view_title)
        text_note=findViewById(R.id.note_view)




    }
}