package cz.stokratandroid.menu1

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
    }

    // udalost vyvolavana pred otevrenim menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // pridat menu na toolbar
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // udalost vyvolana pri kliknuti na polozku menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // zjistime, ktera polozka menu byla zvolena
        // pokud Nastaveni, zobrazit zpravu
        return if (item.itemId == R.id.nastaveni) {
            Toast.makeText(this, "Zvolena položka menu Nastavení", Toast.LENGTH_SHORT).show()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }
}