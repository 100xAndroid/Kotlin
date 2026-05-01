package cz.stokratandroid.intentaplikace

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class IntentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_intent)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ziskat predavany intent
        val myIntent = intent

        // zajima nas pouze intent typu VIEW
        if (myIntent.action == Intent.ACTION_VIEW) {

            // nacist predana data
            val data = myIntent.data

            // data zobrazit
            val txtView = findViewById<View>(R.id.textView) as TextView
            txtView.text = data.toString()
        }
    }
}