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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class signupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
    }

    fun homeScreen(view: View) {
        finish()
    }

    fun submitSignup (view: View) {
        val usernameText = findViewById<EditText>(R.id.usernameText)
        val passwordText = findViewById<EditText>(R.id.passwordText)
        val confirmPasswordText = findViewById<EditText>(R.id.confirmPassword)
        val username = usernameText.text.toString()
        val password = passwordText.text.toString()
        val confirmPassword = confirmPasswordText.text.toString()
        if (username == "") {
            Toast.makeText(this, "Please enter an username", Toast.LENGTH_SHORT).show()
            return
        }
        if (password == "") {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            return
        }
        if (password != confirmPassword) {
            Toast.makeText(this, "The passwords you have entered do not match", Toast.LENGTH_SHORT).show()
            return
        }
        checkForValidLogin(username, password)
        usernameText.setText("")
    }

    private fun checkForValidLogin(username: String, password: String) {
        val users = Firebase.database.getReference("Users")
        val user = users.orderByChild("username").equalTo(username)
        user.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                // empty
            }

            override fun onDataChange(data: DataSnapshot) {
                Log.d("test", "The data is $data")
                processData(username, password, data, users)
            }
        })
    }

    private fun processData(username: String, password: String, arr: DataSnapshot, users: DatabaseReference) {
        if (arr.hasChildren()) {
            Toast.makeText(this, "The username is already taken, please enter a new one", Toast.LENGTH_SHORT).show()
            return
        }
        // val newUser = User(username, password)
        val newUser = users.push()
        newUser.child("username").setValue(username)
        newUser.child("password").setValue(password)
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("username", username)
        startActivity(intent)
    }
}