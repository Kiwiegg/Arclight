package android.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun homeScreen(view: View) {
        finish()
    }

    fun submitLogin(view: View) {
        val usernameText = findViewById<EditText>(R.id.usernameText)
        val passwordText = findViewById<EditText>(R.id.passwordText)
        val username = usernameText.text.toString()
        val password = passwordText.text.toString()
        if (username == "") {
            Toast.makeText(this, "Please enter an username", Toast.LENGTH_SHORT).show()
            return
        }
        if (password == "") {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            return
        }
        checkForValidLogin(username, password)
        passwordText.setText("")
        usernameText.setText("")
    }

    private fun checkForValidLogin(username: String, password: String) {
        val users = Firebase.database.getReference("Users")
        val user = users.orderByChild("username").equalTo(username)
        // Log.d("fbTest", "ruunning")
        user.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // empty
            }

            override fun onDataChange(data: DataSnapshot) {
                Log.d("test", "The data is $data")
                processData(username, password, data)
            }
        })
    }

    private fun processData(username: String, password: String, arr: DataSnapshot) {
        if (!arr.hasChildren()) {
            Toast.makeText(this, "The username does not exist", Toast.LENGTH_SHORT).show()
            return
        }
        val data = arr.children.iterator().next()
        val realPassword = data.child("password").value
        if (password == realPassword) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("username", username)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Incorrect password", Toast.LENGTH_SHORT).show()
        }
    }
}