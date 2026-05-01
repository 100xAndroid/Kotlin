package cz.stokratandroid.widget2

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    // otevrit soubor pro cteni preferenci
    val sharedPreferences = getSharedPreferences("Nastaveni", MODE_PRIVATE)
    // nacist ulozenou hodnotu
    val misto = sharedPreferences.getString("misto", "Karlovy Vary")
    // zapsat hodnotu do pole
    val editText = findViewById<View>(R.id.txtMisto) as EditText
    editText.setText(misto)
}

    // ulozit zvolene mesto do sdilenych preferenci
    fun ulozitNastaveni(view: View?) {
        // nacist hodnoty z formulare
        val editText1 = findViewById<View>(R.id.txtMisto) as EditText
        val inputText = editText1.text.toString()

        // otevrit soubor pro zapis preferenci
        val sharedPreferences = getSharedPreferences("Nastaveni", MODE_PRIVATE)
        // vytvorit objekt editor preferenci
        val editor = sharedPreferences.edit()
        // do editoru ulozit klic a hodnotu z formulare
        editor.putString("misto", inputText)
        // data ulozit
        editor.apply()

        // informace pro uzivatele
        Toast.makeText(applicationContext, "Nastavení uloženo.", Toast.LENGTH_LONG).show()
    }
}
