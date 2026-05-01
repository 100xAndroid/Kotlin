package cz.stokratandroid.internalstorage

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.io.IOException

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

    fun ulozitData(sender: View?) {
        // nacist hodnoty z formulare
        val editText1 = findViewById<View>(R.id.editText1) as EditText
        val inputText = editText1.text.toString()
        val NAZEV_SOUBORU = "test.txt"
        try {
            // otevrit soubor pro zapis
            val outStream = openFileOutput(NAZEV_SOUBORU, MODE_PRIVATE)

            // zapsat text
            outStream.write(inputText.toByteArray())

            // uzavrit soubor
            outStream.close()
        } catch (e: Exception) {
            // zobrazit text, v pripade chyby
            Toast.makeText(this@MainActivity, "Nedaří se zapsat..." + e.message, Toast.LENGTH_SHORT)
                .show()
            return
        }

        // zjistit misto ulozeni souboru
        val umisteni = "Uloženo: " + filesDir.absolutePath + "/" + NAZEV_SOUBORU

        // pro informaci zobrazit umisteni souboru
        Snackbar.make(findViewById(android.R.id.content), umisteni, Snackbar.LENGTH_LONG)
            .setTextMaxLines(5).show()
    }

    fun nacistData(sender: View?) {
        val NAZEV_SOUBORU = "test.txt"
        try {
            // otevrit soubor pro cteni
            val inStream = openFileInput(NAZEV_SOUBORU)

            // vytvorit instanci bufferu
            val nactenyText = StringBuffer()

            // soubor postupne, po znacich, nacist do bufferu
            var znak: Int
            while (inStream.read().also { znak = it } != -1) {
                nactenyText.append(znak.toChar())
            }

            /*
            // ***********************************************
            // Priklad nacteni souboru po radcich.
            // Rychlejsi, ale vhodne pouze pro textove soubory
            // ***********************************************
            // vytvorit instanci readeru a bufferu pro cteni
            var reader = InputStreamReader(inStream)
            var bufReader = BufferedReader(reader)
            var nactenyText = StringBuffer()

            // text postupne, po radcich, nacist do bufferu
            var radekTextu: String?
            while (true) {
                radekTextu = bufReader.readLine() ?: break
                nactenyText.append("$radekTextu\n")
            }
            */

            // ulozit text do formulare
            val editText1 = findViewById<View>(R.id.editText1) as EditText
            editText1.setText(nactenyText)
        } catch (e: IOException) {
            // zobrazit text, v pripade chyby
            val textChyby = """
                Nedaří se načíst.. 
                ${e.message}
                """.trimIndent()
            Toast.makeText(this@MainActivity, textChyby, Toast.LENGTH_SHORT).show()
        }
    }
}