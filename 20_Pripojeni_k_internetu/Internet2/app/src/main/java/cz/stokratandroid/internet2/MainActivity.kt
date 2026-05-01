package cz.stokratandroid.internet2

import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

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

        // pole pro zadani mesta a informaci o pocasi priradit do promennych
        txtMesto = findViewById<View>(R.id.txtMesto) as TextView
        txtPredpoved = findViewById<View>(R.id.txtPredpoved) as TextView
    }

    // nacte data ze serveru a zobrazi je v textovem poli
    fun nacistDataZeServeru(view: View?) {
        // smazat predchozi informaci o pocasi
        txtPredpoved!!.text = ""
        // spustit nacitani dat o pocasi
        if (datovePripojeni() == true) {
            val mesto = txtMesto!!.text.toString().replace(" ", "%20")
            val getData = GetData(this, mesto)
            getData.startAsync()
        }
    }

    // Overi dostupnost datoveho pripojeni
    private fun datovePripojeni(): Boolean {
        val context = applicationContext
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo == null) {
            txtPredpoved!!.text = "Datové připojení není dostupné."
            return false
        }
        return true
    }

    companion object {
        var txtMesto: TextView? = null
        var txtPredpoved: TextView? = null
    }
}