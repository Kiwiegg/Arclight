package android.example.passwordmanager

import android.content.DialogInterface.OnMultiChoiceClickListener
import android.graphics.Color
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddInfoActivity : AppCompatActivity() {
    var checkSelected:BooleanArray = BooleanArray(4)
    val checkListNames = arrayOf("Lowercase?", "Uppercase?", "Numbers?", "Special Chars?")
    var passwordLength = 15

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_info)
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

    fun generateButtonOnClick(view: View) {
        val passwordText = findViewById<EditText>(R.id.passwordText)
        passwordText.setText(random_password(passwordLength, checkSelected[0], checkSelected[1], checkSelected[2], checkSelected[3]))
    }

    fun settingButtonOnclick(view: View) {
        val builder = AlertDialog.Builder(this)
        val parent = LinearLayout(this)
        val lengthText = TextView(this)
        val lengthInput = EditText(this)

        parent.setOrientation(LinearLayout.HORIZONTAL)
        lengthInput.setText(passwordLength.toString())
        lengthInput.setInputType(InputType.TYPE_CLASS_NUMBER);
        lengthText.text = "Length Of Password:"
        lengthText.setTextColor(Color.DKGRAY)
        lengthText.setTextSize(18f)

        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(70, 0, 0, 0)

        parent.addView(lengthText, layoutParams)
        parent.addView(lengthInput)

        builder.setView(parent);
        builder.setTitle("Change Password Preferences")

        builder.setMultiChoiceItems(checkListNames, checkSelected, OnMultiChoiceClickListener { dialog, which, isChecked ->
            checkSelected[which] = isChecked
        })

        builder.setPositiveButton("Save") { _, _ ->
            passwordLength = lengthInput.getText().toString().toInt()
        }
        builder.show()
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