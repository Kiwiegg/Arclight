package android.example.passwordmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun passCheck(view: View) {
        val passwordText = findViewById<EditText>(R.id.homePassword)
        val passwordTitle = findViewById<TextView>(R.id.submitPassword)
        if(passwordText.getText().toString() == "test") {
            passwordText.setText("")
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            passwordTitle.text = "Log In"
        }else{
            passwordText.setText("")
            passwordTitle.text = "Try Again!"
        }
    }
}