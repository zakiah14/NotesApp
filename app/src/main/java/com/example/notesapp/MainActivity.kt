package com.example.notesapp

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity

import com.google.android.material.floatingactionbutton.FloatingActionButton
import  android.app.AlertDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Build
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {
    var Add :FloatingActionButton?=null
    var mRef :DatabaseReference?=null
    var Add_title:EditText ?=null
    var  Add_Note :EditText?=null
    var SaveNewNotes :Button ?=null
  var  NoteList:ArrayList<Notes>? = null
    var titleTextView :TextView ?=null
    var timeTextView:TextView? =null
    var listView : ListView ? =null
    //var btn_signUp :Button ? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectedViews()

        val database =FirebaseDatabase.getInstance()
        mRef =database.getReference("Notes")
//        btn_signUp?.setOnClickListener {
//         var intent_sign =Intent(this@MainActivity,signUp::class.java)
//            startActivity(intent_sign)
//
//        }
        NoteList = ArrayList()
        Add?.setOnClickListener{
            showDialog()
        }
        listView?.onItemClickListener=object :AdapterView.OnItemClickListener{
            override fun onItemClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
               var view_note = NoteList?.get(position)!!
                var title =view_note.title
                    var note=view_note.note

                var intent = Intent (this@MainActivity,viewNote::class.java)
                intent.putExtra("title",title)
                intent.putExtra("note",note)
                startActivity(intent)
            }



        }
listView?.onItemLongClickListener= object : AdapterView.OnItemLongClickListener{
    override fun onItemLongClick(
        parent: AdapterView<*>?,
        view: View?,
        position: Int,
        id: Long
    ): Boolean {
        val alertBulider =AlertDialog.Builder(this@MainActivity)
        val view :View = layoutInflater.inflate(R.layout.deleat_note,null)
        alertBulider.setView(view)

        val alertDialog=alertBulider.create()
        alertDialog.show()
        var mynote=NoteList!!.get(position)!!
        view.findViewById<EditText>(R.id.edit_title_note).setText(mynote.title)
        view.findViewById<EditText>(R.id.edit_note).setText(mynote.note)
        view.findViewById<Button>(R.id.update).setOnClickListener{
          var childmRf = mRef!!.child(mynote!!.id.toString())
            var editTitle= view.findViewById<EditText>(R.id.edit_title_note).text.toString()
            var editenote =view.findViewById<EditText>(R.id.edit_note).text.toString()
            var AfterUpdate=Notes(mynote.id.toString(),editTitle,editenote,getcurrentdata())
            childmRf.setValue(AfterUpdate)
alertDialog.dismiss()
        }
        view.findViewById<Button>(R.id.delate).setOnClickListener{
            mRef!!.child(mynote.id.toString()).removeValue()
            alertDialog.dismiss()
        }
       return false
    }
}
    }

    override fun onStart() {
        super.onStart()
        mRef ?.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                NoteList?.clear()
                for (n in snapshot.children) {
                    var note = n.getValue(Notes::class.java)
                    NoteList?.add(0,note!!)//اول ملاحظة يحطها في اول اندكس

                }
                val noteAdapter =NotAdapter(applicationContext, NoteList!!)
                listView!!.adapter=noteAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }


        }) }


     private fun connectedViews(){
     Add =findViewById(R.id.addbutton)
      Add_title =findViewById(R.id.title_note)
     Add_Note =findViewById(R.id.note)
        SaveNewNotes= findViewById(R.id.seved)
        titleTextView=findViewById(R.id.titleTextView)
        timeTextView =findViewById(R.id.timetextView)
        listView =findViewById(R.id.listView)
        // btn_signUp =findViewById(R.id.btn_signUp)

    }
    fun showDialog(){

        val alertBulider =AlertDialog.Builder(this)
        val view :View = layoutInflater.inflate(R.layout.add_note,null)
        alertBulider.setView(view)

        val alertDialog=alertBulider.create()
        alertDialog.show()


      view.findViewById<Button>(R.id.seved).setOnClickListener {



          alertDialog.dismiss()
    val title =    view.findViewById<EditText>(R.id.title_note).text.toString()
    val Note = view.findViewById<EditText>(R.id.note).text .toString()
    if(title.isNotEmpty() && Note.isNotEmpty()) {
       var id =mRef!!.push ().key
       var myNote =Notes(id!!,title,Note, getcurrentdata())
        mRef!!.child(id).setValue(myNote)


  } else{ Toast.makeText(applicationContext,"Not saved because the title or the note is empty",Toast.LENGTH_LONG).show()
        }
     }


    }
//
//  )



//@RequiresApi(Build.VERSION_CODES.N)

fun getcurrentdata():String{

val  calendar=Calendar.getInstance()
    val mdformat =java.text.SimpleDateFormat("EEEE hh:mm a ")
val strDate=mdformat.format(calendar.time)
    return  strDate
}
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.min,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var id =item?.itemId
        if(id==R.id.item_logout){
            FirebaseAuth.getInstance().signOut()
            val intentToLogin =Intent(this@MainActivity,login::class.java)
            startActivity(intentToLogin)
        }


        return true
    }

    }


