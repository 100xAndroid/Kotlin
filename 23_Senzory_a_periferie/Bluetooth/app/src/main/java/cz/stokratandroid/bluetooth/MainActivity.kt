package cz.stokratandroid.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private val btAdapter = BluetoothAdapter.getDefaultAdapter()

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

    fun zapnoutBluetooth(view: View?) {
        // test jestli ma zarizeni modul Bluetooth
        if (btAdapter == null) {
            Toast.makeText(this, "Zařízení nemá k dispozici Bluetooth.", Toast.LENGTH_SHORT).show()
            return
        }
        // test, jestli je Bluetooth zapnuty
        if (!btAdapter.isEnabled) {
            // zapnout Bluetooth
            val zapnoutBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivity(zapnoutBtIntent)
        }
    }

    fun vypnoutBluetooth(view: View?) {
        // test jestli ma zarizeni modul Bluetooth
        if (btAdapter == null) {
            Toast.makeText(this, "Zařízení nemá k dispozici Bluetooth.", Toast.LENGTH_SHORT).show()
            return
        }
        btAdapter.disable()
    }

    fun udelatViditelne(view: View?) {
        // zapnout viditelnost zarizeni na dve minuty
        val viditelneBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        viditelneBtIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 120)
        startActivity(viditelneBtIntent)
    }

    fun testBluetooth(view: View?) {
        val txtTestBt = findViewById<View>(R.id.txtTestBt) as TextView

        // test pritomnosti modulu BT
        if (btAdapter == null) {
            txtTestBt.text = "Zařízení neobsahuje Bluetooth modul"
            return
        } else {
            txtTestBt.text = "Bluetooth modul OK"
        }

        // test stavu BT
        if (btAdapter.state == BluetoothAdapter.STATE_ON) {
            txtTestBt.text = "${txtTestBt.text} \n Bluetooth je zapnutý."
        } else if (btAdapter.state == BluetoothAdapter.STATE_TURNING_ON) {
            txtTestBt.text = "${txtTestBt.text} \n Bluetooth se zapíná."
            return
        } else if (btAdapter.state == BluetoothAdapter.STATE_TURNING_OFF) {
            txtTestBt.text = "${txtTestBt.text} + \n Bluetooth se vypíná."
            return
        } else if (btAdapter.state == BluetoothAdapter.STATE_OFF) {
            txtTestBt.text = "${txtTestBt.text} \n Bluetooth je vypnutý."
            return
        }

        // test viditelnosti
        if (btAdapter.scanMode == BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE)
            txtTestBt.text = "${txtTestBt.text} \n Zařízení je pro okolí viditelné."
        else
            txtTestBt.text = "${txtTestBt.text} \n Zařízení není pro okolí viditelné."

        // test jestli zařízení vyhledává
        if (btAdapter.isDiscovering)
            txtTestBt.text = "${txtTestBt.text} \n Probíhá vyhledávání okolních zařízení."

        // test sparovanych zarizeni
        val pairedDevices = btAdapter.bondedDevices
        txtTestBt.text = "${txtTestBt.text} \n Spárovaná zařízení."

        if (pairedDevices.size == 0) {
            txtTestBt.text = txtTestBt.text.toString() + " žádné zařízení"
        } else {
            for (device in pairedDevices) {
                val zarizeniJmeno = device.name
                val zarizeniAdresa = device.address
                txtTestBt.text = txtTestBt.text.toString() + "\n   $zarizeniJmeno [$zarizeniAdresa]"
            }
        }
    }

    fun hledatBluetooth(view: View?) {
        val txtOkolniBt = findViewById<View>(R.id.txtOkolniBt) as TextView
        txtOkolniBt.text = ""

        // registrace udalosti nalezeni zarizeni v okoli
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(receiver, filter)

        // zahajit vyhledavani zarizeni
        btAdapter!!.startDiscovery()
    }

    // vytvorit instanci BroadcastReceiver pro hledani BT zarizeni
    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device = intent.getParcelableExtra<BluetoothDevice>(BluetoothDevice.EXTRA_DEVICE)
                val zarizeniJmeno = device!!.name
                val zarizeniAdresa = device.address
                // val zarizeniVerzeBt: String = device.bluetoothClass
                // val zarizeniSparovano: String = device.bondState
                // val zarizeniTyp: String = device.type
                val txtOkolniBt = findViewById<View>(R.id.txtOkolniBt) as TextView
                txtOkolniBt.text =
                    txtOkolniBt.text.toString() + zarizeniJmeno + " [" + zarizeniAdresa + "]\n"
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // odregistrovat receiver
        // neexistuje overeni registrace, takze pouzijeme try - catch
        try {
            unregisterReceiver(receiver)
        } catch (ex: IllegalArgumentException) {
        }
    }

    companion object {
        private const val REQUEST_ENABLE_BT = 1
    }
}