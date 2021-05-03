package android.example.passwordmanager

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList


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

    fun random_password(length: Int, lower: Boolean, upper: Boolean, number: Boolean, special: Boolean): String {
        var password: String = ""
        var arraylist = ArrayList<String>()

        if(lower) arraylist.add("abcdefghijklmnopqrstuvxyz")
        if(upper) arraylist.add("ABCDEFGHIJKLMNOPQRSTUVXYZ")
        if(number) arraylist.add("0123456789")
        if(special) arraylist.add("£$&()*+[]@#^-_!?")

        for(i in 1..length) {
            val temp1 = (0 until arraylist.size).random()
            val temp2 = (0 until arraylist[temp1].length).random()
            password += arraylist[temp1].get(temp2)
        }
        return password
    }

    private fun processData(data: DataSnapshot) {
        val main_listview = findViewById<ListView>(R.id.main_listview)
        val itemsList:ArrayList<Information> = ArrayList()

        // val noPassword = findViewById<TextView>(R.id.noPasswordView)

        if (!data.hasChildren()) {
            // noPassword.visibility = VISIBLE
            return
        }

        // noPassword.visibility = INVISIBLE
        for (child in data.children) {
            // val newInfo = child.getValue(Information::class.java)!!
            itemsList.add(
                Information(
                    child.child("purpose").value.toString(),
                    child.child("username").value.toString(),
                    child.child("password").value.toString()
                )
            )
        }

        val arrayAdapter:CustomArrayAdapter = CustomArrayAdapter(this, 0, itemsList)
        main_listview.adapter = arrayAdapter
    }

    fun addButtonClick (view: View) {
        val username = intent.getStringExtra("username")

        val intent = Intent(this, AddInfoActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}