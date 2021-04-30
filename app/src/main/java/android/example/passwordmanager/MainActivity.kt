package android.example.passwordmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val main_listview = findViewById<ListView>(R.id.main_listview)
        val itemsList:ArrayList<Information> = ArrayList()
        itemsList.add(Information("test1", "test2", "test3"))
        itemsList.add(Information("test4", "test5", "test6"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        itemsList.add(Information("test7", "test8", "test9"))
        val arrayAdapter:CustomArrayAdapter = CustomArrayAdapter(this, 0, itemsList)
        main_listview.adapter = arrayAdapter
    }
}