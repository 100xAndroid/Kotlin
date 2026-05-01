package cz.stokratandroid.sluzba3

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
    private var dataReceiver: DataReceiver? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
    public override fun onResume() {
        super.onResume()
        if (dataReceiver == null)
            dataReceiver = DataReceiver()
        val intentFilter = IntentFilter("zpravaZeSluzby")
        registerReceiver(dataReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    // udalost onPause, volana pri preruseni aktivity
    public override fun onPause() {
        super.onPause()
        if (dataReceiver != null) unregisterReceiver(dataReceiver)
    }

    fun poslatZpravu(view: View?) {
        // ziskat hodnotu z textoveno (numerickeho) pole
        val txtZpravaKOdeslani = findViewById<TextView>(R.id.txtZpravaKOdeslani)
        val intZpravaKOdeslani = txtZpravaKOdeslani.text.toString().toInt()

        // odeslat intent zpravu
        val broadcastIntent = Intent()
        broadcastIntent.setPackage("cz.stokratandroid.sluzba3")
        broadcastIntent.setAction("zpravaZAktivity")
        broadcastIntent.putExtra("data", intZpravaKOdeslani)
        sendBroadcast(broadcastIntent)
    }

    // trida prijimajici data odeslana sluzbou
    internal inner class DataReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == "zpravaZeSluzby") {
                val data = intent.getIntExtra("data", -1)
                prijataZprava!!.text = "Přijatá zpráva: $data"
            }
        }
    }
}