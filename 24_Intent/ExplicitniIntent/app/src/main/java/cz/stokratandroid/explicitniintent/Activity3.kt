package cz.stokratandroid.explicitniintent

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Activity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // nacteni intentu
        val mujIntent = intent
        // nacteni prvniho, textoveho, parametru
        val parametr1 = mujIntent.getStringExtra("param1")
        // nacteni druheho, ciselneho, parametru
        val parametr2 = mujIntent.getIntExtra("param2", 0)

        // zapis nactenych parametru do textovych poli
        val txtView1 = findViewById<View>(R.id.textView1) as TextView
        txtView1.text = parametr1
        val txtView2 = findViewById<View>(R.id.textView2) as TextView
        txtView2.text = parametr2.toString()
    }

    // zavrit aktivitu a vratit se na predchozi
    fun zpet(view: View?) {
        finish()
    }
}