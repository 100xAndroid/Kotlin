package cz.stokratandroid.scrollview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // nacist data do komponenty textView text
        naplnitScrollView()
    }

    // nastavit text do textoveho pole
    private fun naplnitScrollView() {
        // ziskat komponentu textView
        val textField = findViewById<View>(R.id.textView1) as TextView
        // do textView vlozit text definovany v resourcech string
        textField.setText(R.string.verzeAndroidu)
    }
}