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
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Text
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = intent.getStringExtra("username")
        Log.d("username", user!!)

        val info = Firebase.database.getReference("savedInfo/$user")
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

        val noPassword = findViewById<TextView>(R.id.noPasswordView)

        if (!data.hasChildren()) {
            noPassword.visibility = VISIBLE
            return
        }

        noPassword.visibility = INVISIBLE

        for (child in data.children) {
            // val newInfo = child.getValue(Information::class.java)!!
            itemsList.add(
                Information(
                    child.child("purpose").value.toString(),
                    child.child("username").value.toString(),
                    child.child("password").value.toString(), intent.getStringExtra("username"))
            )
        }

        val arrayAdapter = CustomArrayAdapter(this, 0, itemsList)
        main_listview.adapter = arrayAdapter

    }

    fun addButtonClick (view: View) {
        val user = intent.getStringExtra("username")
        val intent = Intent(this, AddInfoActivity::class.java)
        intent.putExtra("username", user)
        overridePendingTransition(0, 0)
        startActivity(intent)
    }
}