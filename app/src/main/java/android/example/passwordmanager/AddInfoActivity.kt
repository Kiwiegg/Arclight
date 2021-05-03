package android.example.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)
    }

    fun saveButtonOnclick(view: View) {
        val purpose = findViewById<EditText>(R.id.purposeText).text.toString()
        val username = findViewById<EditText>(R.id.usernameText).text.toString()
        val password = findViewById<EditText>(R.id.passwordText).text.toString()

        val user = intent.getStringExtra("username")
        val fb = Firebase.database.getReference("savedInfo/$user")
        fb.push().setValue(Information(purpose, username, password))

        finish()
    }

    fun cancel(view: View) {
        finish()
    }
}