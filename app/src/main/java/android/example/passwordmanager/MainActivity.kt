package android.example.passwordmanager

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
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

        /*if (!data.hasChildren()) {
            val noPassword = findViewById<TextView>(R.id.noPasswordView)
            noPassword.visibility = VISIBLE
            return
        }*/

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

        val footerId = 123
        val footer = Button(this)
        val cancel = Button(this)
        val save = Button(this)
        val parent = LinearLayout(this)

        footer.setText("ADD NEW ENTRY")
        footer.setId(footerId)
        main_listview.addFooterView(footer);

        footer.setOnClickListener {
            // New purpose and username text
            parent.setOrientation(LinearLayout.VERTICAL)

            val new_purpose = EditText(this)
            val new_username = EditText(this)
            new_purpose.hint = "Purpose"
            new_username.hint = "Username"

            new_purpose.setTextColor(Color.WHITE)
            new_username.setTextColor(Color.WHITE)

            new_purpose.setHintTextColor(Color.WHITE)
            new_username.setHintTextColor(Color.WHITE)
            new_purpose.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
            new_username.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);

            // Save and Cancel Buttons
            val child = LinearLayout(this)
            child.setOrientation(LinearLayout.HORIZONTAL)
            child.setWeightSum(2f);

            val shape1 = GradientDrawable()
            shape1.setCornerRadius(32f)
            shape1.setColor(Color.GREEN)

            val shape2 = GradientDrawable()
            shape2.setCornerRadius(32f)
            shape2.setColor(Color.RED)

            val param = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT,
                1.0f
            )

            val cancel_ID = 1234
            val save_ID = 12345
            cancel.setText("CANCEL")
            save.setText("SAVE")
            cancel.setId(cancel_ID)
            save.setId(save_ID)
            cancel.setTextColor(Color.WHITE);
            save.setTextColor(Color.WHITE);

            cancel.setBackground(shape2)
            save.setBackground(shape1)

            cancel.setLayoutParams(param);
            save.setLayoutParams(param);

            child.addView(cancel)
            child.addView(save)
            parent.addView(new_purpose)
            parent.addView(new_username)
            parent.addView(child)

            main_listview.removeFooterView(footer)
            main_listview.addFooterView(parent)
        }

        cancel.setOnClickListener {
            main_listview.removeFooterView(parent)
            main_listview.addFooterView(footer)
            val intent = intent
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
        }

        save.setOnClickListener {
            main_listview.removeFooterView(parent)
            main_listview.addFooterView(footer)
            val intent = intent
            finish()
            overridePendingTransition(0, 0)
            startActivity(intent)
        }
    }
}