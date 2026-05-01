package cz.stokratandroid.velikostpameti

import android.app.ActivityManager
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.File

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

        // velikost pameti
        val actManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val memInfo = ActivityManager.MemoryInfo()
        actManager.getMemoryInfo(memInfo)
        val operacniPametCelkem = memInfo.totalMem.toFloat() / (1000 * 1000 * 1000)
        val operacniPametVolna = memInfo.availMem.toFloat() / (1000 * 1000 * 1000)

        // velikost uloziste
        val cesta = Environment.getDataDirectory()
        val stat = StatFs(cesta.path)
        val ulozisteCelkem = stat.totalBytes.toFloat() / (1000 * 1000 * 1000)
        val ulozisteVolne = stat.freeBytes.toFloat() / (1000 * 1000 * 1000)

        // velikost uloziste, alternativni zpusob
        val cesta2 = Environment.getDataDirectory()
        val stat2 = StatFs(cesta2.path)
        val velikostBloku = stat2.blockSizeLong
        val celkemBloku = stat2.blockCountLong
        val volnychBloku = stat2.availableBlocksLong
        val ulozisteCelkem2 = celkemBloku.toFloat() * velikostBloku / (1000 * 1000 * 1000)
        val ulozisteVolne2 = volnychBloku.toFloat() * velikostBloku / (1000 * 1000 * 1000)

        // velikost uloziste, druhy alternativni zpusob
        val soubor = File(filesDir.absoluteFile.toString())
        val ulozisteCelkem3 = soubor.totalSpace.toFloat() / (1000 * 1000 * 1000)
        val ulozisteVolne3 = soubor.freeSpace.toFloat() / (1000 * 1000 * 1000)

        // zobrazeni vysledku
        val txtPametCelkem = findViewById<View>(R.id.txtPametCelkem) as TextView
        val txtPametVolna = findViewById<View>(R.id.txtPametVolna) as TextView
        val txtUlozisteCelkem = findViewById<View>(R.id.txtUlozisteCelkem) as TextView
        val txtUlozisteVolne = findViewById<View>(R.id.txtUlozisteVolne) as TextView
        txtPametCelkem.text = String.format("%.02f GB", operacniPametCelkem)
        txtPametVolna.text = String.format("%.02f GB", operacniPametVolna)
        txtUlozisteCelkem.text = String.format("%.02f GB", ulozisteCelkem)
        txtUlozisteVolne.text = String.format("%.02f GB", ulozisteVolne)
    }
}