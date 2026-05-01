package cz.stokratandroid.souboryprojektu

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

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

    fun nacistRes(sender: View?) {
        // nazev souboru a jeho umisteni
        val NAZEV_SOUBORU = "tmp_test.txt"
        val adresar = baseContext.cacheDir
        try {
            // otevrit soubor pro cteni
            val r = getResources()
            val inStream = r.openRawResource(R.raw.test_raw)
            var nactenyText = ""

            // vytvorit instanci bufferu
            val buff = BufferedReader(InputStreamReader(inStream))

            // soubor postupne, po radcich, nacist do bufferu
            var radka: String? = ""
            while (buff.readLine().also { radka = it } != null) {
                nactenyText = nactenyText + radka + "\n"
            }
            /*
            do {
                nactenyText = nactenyText + radka + "\n"
                radka = buff.readLine()
            } while (radka != null)
            */

            // ulozime text do formulare
            val editText1 = findViewById<View>(R.id.editText1) as EditText
            editText1.setText(nactenyText)
        } catch (e: IOException) {
            // zobrazit informaci o chybe
            val textChyby = """
            Nedaří se načíst..
            ${e.message}
            """.trimIndent()
            Toast.makeText(this@MainActivity, textChyby, Toast.LENGTH_SHORT).show()
        }
    }

    fun nacistAssets(sender: View?) {
        // nazev souboru a jeho umisteni
        val NAZEV_SOUBORU = "tmp_test.txt"
        val adresar = baseContext.cacheDir
        try {
            // otevreme soubor pro cteni
            val context = applicationContext
            val inStream = context.assets.open("test_assets.txt")
            var nactenyText = ""

            // vytvorit instanci bufferu
            val buff = BufferedReader(InputStreamReader(inStream))

            // soubor postupne, po radkach, nacist do bufferu
            var radka: String? = ""
            while (buff.readLine().also { radka = it } != null) {
                nactenyText = nactenyText + radka + "\n"
            }

            // ulozit text do formulare
            val editText1 = findViewById<View>(R.id.editText1) as EditText
            editText1.setText(nactenyText)
        } catch (e: IOException) {
            // zobrazi informaci o chybe
            val textChyby = """
            Nedaří se načíst..
            ${e.message}
            """.trimIndent()
            Toast.makeText(this@MainActivity, textChyby, Toast.LENGTH_SHORT).show()
        }
    }
}