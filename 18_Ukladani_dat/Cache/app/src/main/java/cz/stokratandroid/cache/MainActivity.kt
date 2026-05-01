package cz.stokratandroid.cache

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.FileInputStream
import java.io.FileWriter
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
    // nacteni hodnot z formulare
    val editText1 = findViewById<View>(R.id.editText1) as EditText
    val inputText = editText1.text.toString()

    // nazev souboru a jeho umisteni
    val NAZEV_SOUBORU = "tmp_test.txt"
    val adresar = baseContext.cacheDir
    // val adresar = baseContext.externalCacheDir

    // zapis dat do souboru
    val file = File(adresar, NAZEV_SOUBORU)
    var writer: FileWriter? = null
    try {
        writer = FileWriter(file)
        writer.write(inputText)
        writer.close()
    } catch (e: IOException) {
        val txtChyba = "Data se nepodařilo zapsat. " + e.message
        Toast.makeText(this@MainActivity, txtChyba, Toast.LENGTH_SHORT).show()
        return
    }
    // zjistit misto ulozeni souboru
    val umisteni = "Uloženo: $adresar/$NAZEV_SOUBORU"
    // pro informaci zaobrazit umisteni souboru
    Snackbar.make(findViewById(android.R.id.content), umisteni, Snackbar.LENGTH_LONG)
        .setTextMaxLines(5).show()
}

    fun nacistData(sender: View?) {
        // nazev souboru a jeho umisteni
        val NAZEV_SOUBORU = "tmp_test.txt"
        val adresar = baseContext.cacheDir
        // val adresar = baseContext.externalCacheDir

        try {
            // otevrit soubor pro cteni
            val soubor = File(adresar, NAZEV_SOUBORU)
            val inStream = FileInputStream(soubor)

            // vytvorit instanci bufferu
            val nactenyText = StringBuffer()

            // soubor postupne, po znacich, nacist do bufferu
            var znak: Int
            while (inStream.read().also { znak = it } != -1) {
                nactenyText.append(znak.toChar())
            }

            // ulozit text do formulare
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
}