package android.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

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
        if (checkForValidLogin(username, password)) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid Username or Incorrect Password", Toast.LENGTH_SHORT).show()
            passwordText.setText("")
            usernameText.setText("")
        }
    }

    private fun checkForValidLogin(username: String, password: String): Boolean {
        return username == "login" && password == "password"
    }
}