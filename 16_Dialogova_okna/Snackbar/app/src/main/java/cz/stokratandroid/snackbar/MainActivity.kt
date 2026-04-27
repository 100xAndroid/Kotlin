package cz.stokratandroid.snackbar

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

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
    }

    // nejjednodussi verze snackbar zpravy
    fun SnackbarDialog1(sender: View?) {
        val view = findViewById<View>(android.R.id.content)
        Snackbar.make(view, "Snackbar - test", Snackbar.LENGTH_LONG).show()
    }

    // snackbar zprava s tlacitkem
    fun SnackbarDialog2(sender: View?) {
        // posluchac udalosti kliknuti na tlacitko
        val onClickListener: View.OnClickListener
        onClickListener = View.OnClickListener {
            Toast.makeText(
                applicationContext,
                "Kliknuto na tlačítko snackbaru",
                Toast.LENGTH_LONG
            ).show()
        }

        // definice zpravy
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "Snackbar - test",
            Snackbar.LENGTH_LONG
        )

        // nastaveni tlacitka
        snackbar.setAction("Tlačítko", onClickListener)
        snackbar.setActionTextColor(Color.YELLOW)

        // nastaveni barvy snackbaru
        val snackbarView = snackbar.view
        snackbarView.setBackgroundColor(Color.GRAY)

        // nastaveni barvy textu
        val textView = snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        textView.setTextColor(Color.WHITE)

        // nastaveni maximalniho poctu radku (standardne zobrazi max dva radky)
        textView.maxLines = 5

        // zobrazeni snackbaru
        snackbar.show()
    }
}
