package android.example.passwordmanager

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setGradient()
    }

    private fun setGradient() {
        val appName = findViewById<TextView>(R.id.appName)
        val textShader = LinearGradient(
            0.toFloat(), 0.toFloat(), appName.width.toFloat(), appName.textSize,
            Color.parseColor("#EECDA3"), Color.parseColor("#EF629F"), Shader.TileMode.CLAMP
        )
        appName.paint.setShader(textShader)
    }

    fun loginWindow(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}