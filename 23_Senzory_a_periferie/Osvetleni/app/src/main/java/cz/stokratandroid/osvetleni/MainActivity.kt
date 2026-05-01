package cz.stokratandroid.osvetleni

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), SensorEventListener {
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

    var sensorManager: SensorManager? = null
    private var txtIntenzita: TextView? = null
    private var txtPresnost: TextView? = null
    override fun onResume() {
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        val senzorOsvetleni = sensorManager!!.getDefaultSensor(Sensor.TYPE_LIGHT)
        if (senzorOsvetleni == null) {
            Toast.makeText(
                applicationContext,
                "Senzor osvětlení není k dispozici.",
                Toast.LENGTH_LONG
            ).show()
        } else {
            sensorManager!!.registerListener(
                this,
                senzorOsvetleni,
                SensorManager.SENSOR_DELAY_NORMAL
            )
        }
        txtIntenzita = findViewById<View>(R.id.txtIntenzita) as TextView
        txtPresnost = findViewById<View>(R.id.txtPresnost) as TextView
        super.onResume()
    }

    override fun onPause() {
        sensorManager!!.unregisterListener(this)
        super.onPause()
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_LIGHT) {
            txtIntenzita!!.text = "Intenzita osvětlení: " + event.values[0]
        }
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        if (sensor.type == Sensor.TYPE_LIGHT) {
            txtPresnost!!.text = "Přesnost: $accuracy"
        }
    }
}
