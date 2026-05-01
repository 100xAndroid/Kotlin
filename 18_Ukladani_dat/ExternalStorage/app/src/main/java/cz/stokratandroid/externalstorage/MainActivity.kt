package cz.stokratandroid.externalstorage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
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

        povoleniPristupu()
    }

    fun ulozitData(sender: View?) {
        // overit, jestli je mozne externi uloziste pouzit
        val stavExternihoMedia = kontrolaStavuExternihoMedia()
        if (stavExternihoMedia.length > 0) {
            Toast.makeText(this@MainActivity, stavExternihoMedia, Toast.LENGTH_SHORT).show()
            return
        }

        // nacteni hodnot z formulare
        val editText1 = findViewById<View>(R.id.editText1) as EditText
        val inputText = editText1.text.toString()

        // nazev souboru a jeho umisteni
        val NAZEV_SOUBORU = "test.txt"
        val adresar = Environment.getExternalStorageDirectory().absolutePath

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
        // overit, jestli je mozne externi uloziste pouzit
        val stavExternihoMedia = kontrolaStavuExternihoMedia()
        if (stavExternihoMedia.length > 0) {
            Toast.makeText(
                this@MainActivity, stavExternihoMedia,
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // nazev souboru a jeho umisteni
        val NAZEV_SOUBORU = "test.txt"
        val adresar = Environment.getExternalStorageDirectory()
            .absolutePath
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
            val textChyby = "Nedaří se načíst..${e.message}".trimIndent()
            Toast.makeText(this@MainActivity, textChyby, Toast.LENGTH_SHORT).show()
        }
    }

    // funkce pozada o pravo pristupu k externim souborum
    private fun povoleniPristupu() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                val intent = Intent()
                intent.setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                val uri = Uri.fromParts("package", this.packageName, null)
                intent.setData(uri)
                startActivity(intent)
            }
        }
    }

    // funkce zjistí, jestli je mozne externi zarizeni pouzit ke cteni/zapisu
    private fun kontrolaStavuExternihoMedia(): String {
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED == state) {
            // data lze cist a zapisovat
            return ""
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY == state) {
            return "Data lze z externího média pouze číst."
        } else {
            return "Data nelze na externí médium zapisovat ani z něj číst."
        }
    }
}