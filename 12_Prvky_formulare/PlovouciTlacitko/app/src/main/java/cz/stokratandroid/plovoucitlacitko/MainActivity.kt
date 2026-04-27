package cz.stokratandroid.plovoucitlacitko

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // registrace listeneru kliknuti na tlacitko
        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            Toast.makeText(
                applicationContext,
                "Kliknuto na plovoucí tlačítko.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}