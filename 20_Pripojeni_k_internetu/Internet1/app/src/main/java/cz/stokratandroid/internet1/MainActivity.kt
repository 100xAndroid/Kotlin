package cz.stokratandroid.internet1

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
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

        jsonTextView = findViewById<View>(R.id.jsonTextView) as TextView
    }

    // nacte data ze serveru a zobrazi je v textovem poli
    fun nacistDataZeServeru(view: View?) {
        if (datovePripojeni() == true) {
            val getData = GetData()
            getData.startAsync()
        }
    }

    // smaze data z textoveho pole
    fun SmazatData(view: View?) {
        jsonTextView!!.text = ""
    }

    // Overi dostupnost datoveho pripojeni
    private fun datovePripojeni(): Boolean {
        val context = applicationContext
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            ?: return false

        // API verze 21 a vyssi
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val capabilities = cm.getNetworkCapabilities(cm.activeNetwork)
            if (capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
            ) return true
        } else {
            val netInfo = cm.activeNetworkInfo
            return netInfo != null && netInfo.isConnected
        }
        return false
    }

    companion object {
        var jsonTextView: TextView? = null
    }
}