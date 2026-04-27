package cz.stokratandroid.tlacitka

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<View>(R.id.tlacitko1) as Button
        button.setOnClickListener {
            // kod, ktery se provede po stisknuti tlacitka
        }
    }

    fun tlacitko_click(view: View?) {
        // kod, ktery se provede po stisknuti tlacitka
    }
}