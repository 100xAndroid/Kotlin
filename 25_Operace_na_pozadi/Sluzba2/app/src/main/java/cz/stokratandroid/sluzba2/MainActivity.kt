package cz.stokratandroid.sluzba2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private var prijataZprava: TextView? = null
    private var dataUpdateReceiver: DataUpdateReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prijataZprava = findViewById(R.id.txtPrijataZprava)
    }

    // kliknuti na tlacitko Spustit sluzbu
    fun spustitSluzbu(view: View?) {
        val intent = Intent(this, Sluzba::class.java)
        startService(intent)
    }

    // kliknuti na tlacitko Zastavit sluzbu
    fun zastavitSluzbu(view: View?) {
        val intent = Intent(this, Sluzba::class.java)
        stopService(intent)
    }

    // udalost onResume, volana pri spusteni aktivity
    override fun onResume() {
        super.onResume()
        if (dataUpdateReceiver == null)
            dataUpdateReceiver = DataUpdateReceiver()
        val intentFilter = IntentFilter("test")
        registerReceiver(dataUpdateReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    // udalost onPause, volana pri preruseni aktivity
    override fun onPause() {
        super.onPause()
        if (dataUpdateReceiver != null)
            unregisterReceiver(dataUpdateReceiver)
    }

    // trida prijimajici data odeslana sluzbou
    internal inner class DataUpdateReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "test") {
                val data = intent.getIntExtra("data", -1)
                prijataZprava!!.text = "Přijatá zpráva: $data"
                // Toast.makeText(context, "Prijata zprava: " + data, Toast.LENGTH_SHORT).show();
            }
        }
    }
}