package cz.stokratandroid.nestedscrollview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // nacist data do komponenty textView text
        naplnitNestedScrollView()
    }

    // nastavit text do textoveho pole
    private fun naplnitNestedScrollView() {
        // ziskat komponentu textView
        val textField = findViewById<View>(R.id.textView2) as TextView
        // do textView vlozit text definovany v resourcech string
        textField.setText(R.string.verzeAndroidu)
    }
}