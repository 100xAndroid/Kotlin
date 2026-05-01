package cz.stokratandroid.stavbaterie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var txtStavAktualni: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        zjistitStavBaterie()

        // najit TextView a ulozit odkaz do promenne
        txtStavAktualni = findViewById<View>(R.id.txtStavAktualni) as TextView
        // zaregistrovat receiver zmeny urovne nabiti baterie
        this.registerReceiver(mBatInfoReceiver, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private fun zjistitStavBaterie() {
        // zjistit stav pomoci tridy BatteryManager
        val bm = getSystemService(BATTERY_SERVICE) as BatteryManager
        val stavBaterie = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)

        // zapsat stav do TextView na  formulari
        val txtStav = findViewById<View>(R.id.txtStavSpusteni) as TextView
        txtStav.text = String.format("Stav při spuštění: %d%%", stavBaterie)
    }

    private val mBatInfoReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // zjistit uroven
            val level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 100)
            // zjistit max rozsah
            val scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 100)
            // spocitat aktualni stav
            val stavBaterie = level * 100 / scale
            // zapsat vysledek do TextView na fomrulari
            txtStavAktualni!!.text = "$stavBaterie%"
        }
    }
}