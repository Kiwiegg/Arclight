package android.example.passwordmanager

import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View.VISIBLE
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val username = intent.getStringExtra("username")
        Log.d("username", username!!)

        val info = Firebase.database.getReference("savedInfo/$username")
        info.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                // empty
            }

            override fun onDataChange(data: DataSnapshot) {
                Log.d("testData", "The data is $data")
                processData(data)
            }
        })


    }

    private fun processData(data: DataSnapshot) {
        val main_listview = findViewById<ListView>(R.id.main_listview)
        val itemsList:ArrayList<Information> = ArrayList()

        if (!data.hasChildren()) {
            val noPassword = findViewById<TextView>(R.id.noPasswordView)
            noPassword.visibility = VISIBLE
            return
        }

        for (child in data.children) {
            // val newInfo = child.getValue(Information::class.java)!!
            itemsList.add(Information(child.child("purpose").value.toString(),
                                      child.child("username").value.toString(),
                                      child.child("password").value.toString()))
        }
        val arrayAdapter:CustomArrayAdapter = CustomArrayAdapter(this, 0, itemsList)
        main_listview.adapter = arrayAdapter
    }
}