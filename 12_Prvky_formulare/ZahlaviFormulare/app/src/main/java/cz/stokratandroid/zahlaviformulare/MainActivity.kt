package cz.stokratandroid.zahlaviformulare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // nastaveni Toolbaru
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // nastaveni textu zahlavi
        // supportActionBar!!.title = "Test"

        // nastaveni barvy zahlavi
        // val barvaZahlavi = ColorDrawable(Color.argb(128, 255, 0, 0))
        // supportActionBar!!.setBackgroundDrawable(barvaZahlavi)
    }
}