package cz.stokratandroid.datovepripojeni

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
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

        txtStav = findViewById<View>(R.id.txtStav) as TextView

        // val stavPripojeni = zjistitStavPripojeni(this)
        // txtStav!!.text = stavPripojeni

        // zaregistrovat receiver pro zmenu typu pripojeni (pokud zatim zaregistrovany neni)
        if (!broadcastAktivni) {
            val intentFilter = IntentFilter()
            intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            registerReceiver(broadcastReceiver, intentFilter)
            broadcastAktivni = true
        }
    }

    var broadcastAktivni = false
    private fun zjistitStavPripojeni(context: Context): String {
        val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val pripojeni = cm.activeNetwork ?: return "Bez připojení"
        val capabilities = cm.getNetworkCapabilities(pripojeni)
        if (capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
            return "Mobilní síť"
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) // || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
            return "WiFi"
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))
            return "Bluetooth"
        else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
            return "Ethernet"
        else
            return "Jiné"
    }

    companion object {
        var txtStav: TextView? = null

        // zachytavani zmeny typu datoveho pripojeni
        private val broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val cm = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
                val pripojeni = cm.activeNetwork
                if (pripojeni == null) {
                    txtStav!!.text = "Bez připojení"
                    return
                }
                val capabilities = cm.getNetworkCapabilities(pripojeni)
                if (capabilities!!.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                    txtStav!!.text = "Mobilní síť"
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) // || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE))
                    txtStav!!.text = "WiFi"
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH))
                    txtStav!!.text = "Bluetooth"
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                    txtStav!!.text = "Ethernet"
                else
                    txtStav!!.text = "Jiné"
            }
        }
    }
}