package com.example.notesapp

import android.content.Context
import android.provider.ContactsContract
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.ArrayList

class NotAdapter( context: Context,noteList: ArrayList<Notes>):
    ArrayAdapter<Notes>(context, R.layout.note_layout, noteList){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
  val view =LayoutInflater.from((context)).inflate(R.layout.note_layout,parent,false)

val note: Notes? =getItem(position)

        view.findViewById<TextView>(R.id.titleTextView).text=note?.title
        view.findViewById<TextView>(R.id.timetextView).text=note?.timestamp.toString()

        return view
    }

}